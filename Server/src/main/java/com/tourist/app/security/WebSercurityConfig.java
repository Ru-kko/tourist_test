package com.tourist.app.security;

import java.util.List;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
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

    http
        .cors().and()
        .csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers("/register").permitAll()
        .requestMatchers(HttpMethod.GET, "/tourist/**", "/trip/**", "/city/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/city/{cityid}").authenticated()
        .requestMatchers(HttpMethod.PUT, "/tourist/**").authenticated()
        .anyRequest().hasRole("ADMIN")
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(filter)
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

    if (!Config.getCors()) {
      http.cors().disable();
    }
    return http.build();
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOriginPattern("*");
    config.setMaxAge(60l);
    config.setAllowedMethods(List.of("POST", "OPTIONS", "GET", "DELETE", "PUT"));
    config.setAllowedHeaders(List.of("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
    source.registerCorsConfiguration("/**", config);

    return new CorsFilter(source);
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
