package com.tourist.app.utils;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tourist.app.dataBase.users.User;
import com.tourist.app.entity.GenerateUserDetails;
import com.tourist.app.services.db.Users;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

  @Autowired
  private Users usrService;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
      FilterChain filterChain) throws ServletException, IOException {

    String bearerTk = req.getHeader("Authorization");

    if (bearerTk == null || !bearerTk.startsWith("Bearer ")) {
      filterChain.doFilter(req, res);
      return;
    }

    bearerTk = bearerTk.replace("Bearer ", "");

    TokenGenerator generator = new TokenGenerator(bearerTk);

    Optional<User> userInfo = usrService.getByIdCard(generator.GetUsername());

    if (userInfo.isPresent()) {
      UsernamePasswordAuthenticationToken usernamePAT = generator.getAuth(new GenerateUserDetails(userInfo.get()));
      SecurityContextHolder.getContext().setAuthentication(usernamePAT);
    }
    filterChain.doFilter(req, res);
  }

}
