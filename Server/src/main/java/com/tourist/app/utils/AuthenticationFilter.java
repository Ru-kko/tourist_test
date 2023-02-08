package com.tourist.app.utils;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourist.app.entity.Credentials;
import com.tourist.app.entity.GenerateUserDetails;
import com.tourist.app.entity.TokenResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
      throws AuthenticationException {
    var credentials = new Credentials();

    try {
      credentials = new ObjectMapper().readValue(req.getReader(), Credentials.class);
    } catch (IOException err) {
      // Do nothing
    }

    UsernamePasswordAuthenticationToken usernameAuthTk = new UsernamePasswordAuthenticationToken(
        credentials.getCardId(),
        credentials.getPassword(), Collections.emptyList());

    return getAuthenticationManager().authenticate(usernameAuthTk);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {

    final GenerateUserDetails userDetails = (GenerateUserDetails) authResult.getPrincipal();
    Boolean admin = false;

    for (GrantedAuthority i : userDetails.getAuthorities()) {
      if (i.getAuthority().equals("ROLE_ADMIN")) {
        admin = true;
        break;
      }
    }

    String token = TokenGenerator.createToken(userDetails.getUsername());
    TokenResponse response = new TokenResponse(token, userDetails.getUsername(), "Bearer ", admin);
    ObjectMapper mapper = new ObjectMapper();
    String stringData = mapper.writeValueAsString(response);

    res.getWriter().write(stringData);
    res.getWriter().flush();

    super.successfulAuthentication(req, res, chain, authResult);
  }
}
