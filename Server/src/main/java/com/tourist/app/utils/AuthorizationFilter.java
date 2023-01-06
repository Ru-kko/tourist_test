package com.tourist.app.utils;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
      FilterChain filterChain) throws ServletException, IOException {

    String bearerTk = req.getHeader("Authorization");

    if (bearerTk != null && bearerTk.startsWith("Bearer ")) {

      bearerTk = bearerTk.replace("Bearer", "");
      UsernamePasswordAuthenticationToken usernamePAT = TokenGenerator.getAuth(bearerTk);
      SecurityContextHolder.getContext().setAuthentication(usernamePAT);
    }
    filterChain.doFilter(req, res);
  }

}
