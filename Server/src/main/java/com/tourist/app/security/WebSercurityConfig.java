package com.tourist.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.tourist.app.services.UserDetailService;
import com.tourist.app.utils.AuthenticationFilter;
import com.tourist.app.utils.AuthorizationFilter;
import com.tourist.app.utils.Config;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class WebSercurityConfig {

  @Autowired
  private UserDetailService userDetailService;
  @Autowired
  private AuthorizationFilter authFilter;

  @Bean
  SecurityFilterChain setFilterChains(HttpSecurity http, AuthenticationManager manager) throws Exception {
    var filter = new AuthenticationFilter();
    filter.setAuthenticationManager(manager);
    filter.setFilterProcessesUrl("/login");

    if (!Config.getCors()) {
      http.cors().disable();
    }
    
    return http
        .csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers("/register").permitAll()
        .requestMatchers(HttpMethod.GET, "/tourist/**", "/trip/**", "/city/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/trip/**").hasRole("USER")
        .requestMatchers(HttpMethod.PUT, "/tourist/**").authenticated()
        .anyRequest().hasRole("ADMIN")
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(filter)
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  AuthenticationManager authManage(HttpSecurity http, PasswordEncoder pswEncoder) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailService)
        .passwordEncoder(pswEncoder)
        .and()
        .build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
