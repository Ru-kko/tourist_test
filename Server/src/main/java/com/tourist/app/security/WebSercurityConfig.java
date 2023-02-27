package com.tourist.app.security;

import java.util.List;

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
  /**
   * It sets the filter chains for the application.
   * 
   * @param http the HttpSecurity object
   * @param manager the AuthenticationManager that will be used to authenticate the user
   * @param authFilter The filter that will be used to authenticate the user.
   * @return A SecurityFilterChain
   */
  @Bean
  SecurityFilterChain setFilterChains(HttpSecurity http, AuthenticationManager manager, AuthorizationFilter authFilter) throws Exception {
    var filter = new AuthenticationFilter();
    filter.setAuthenticationManager(manager);
    filter.setFilterProcessesUrl("/login");

    http
        .cors().and()
        .csrf().disable()
        .authorizeHttpRequests()
        // set role permissions
        .requestMatchers("/register").permitAll()
        .requestMatchers(HttpMethod.GET, "/tourist","/tourist/born", "/trip/**", "/city/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/tourist/@me").authenticated()
        .requestMatchers(HttpMethod.GET, "/tourist/{userid}").permitAll()
        .requestMatchers(HttpMethod.POST, "/city/{cityid}").authenticated()
        .requestMatchers(HttpMethod.PUT, "/tourist/**", "/tourist").authenticated()
        .requestMatchers(HttpMethod.DELETE, "/tourist").authenticated()
        .anyRequest().hasRole("ADMIN")
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(filter)
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

    // check if cors option is disable to developer or else, production
    if (Boolean.FALSE.equals(Config.getCors())) {
      http.cors().disable();
    }
    return http.build();
  }

  /**
   * This function allows the server to accept requests from any origin, if is disabled cors property
   * 
   * @return A CorsFilter object.
   */
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setMaxAge(60l);
    config.setAllowedMethods(List.of("POST", "OPTIONS", "GET", "DELETE", "PUT"));
    config.setAllowedHeaders(List.of("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
    if (Boolean.FALSE.equals(Config.getCors())) {
      config.addAllowedOriginPattern("*");
      source.registerCorsConfiguration("/**", config);
    }

    return new CorsFilter(source);
  }

  /**
   * It creates an AuthenticationManager bean.
   * 
   * @param http The HttpSecurity object that is used to configure the security of the application.
   * @param pswEncoder The password encoder to use.
   * @param userDetailService This is the service that will be used to load the user's details.
   * @return AuthenticationManager
   */
  @Bean
  AuthenticationManager authManage(HttpSecurity http, PasswordEncoder pswEncoder, UserDetailService userDetailService) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailService)
        .passwordEncoder(pswEncoder)
        .and()
        .build();
  }

  /**
   * It creates a password encoder.
   * 
   * @return A new instance of BCryptPasswordEncoder.
   */
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
