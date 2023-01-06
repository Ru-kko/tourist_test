package com.tourist.app.utils;

import java.util.Collections;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenGenerator {
  public static String createToken(String cardId) {
    Date expDate = new Date(System.currentTimeMillis() + Config.getExpirationTime());

    return Jwts.builder()
        .setSubject(cardId)
        .setExpiration(expDate)
        .signWith(Keys.hmacShaKeyFor(Config.getSecret().getBytes()))
        .compact();
  }

  public static UsernamePasswordAuthenticationToken getAuth(String token) {
    try {
      Claims claims = Jwts.parserBuilder()
          .setSigningKey(Config.getSecret().getBytes())
          .build()
          .parseClaimsJws(token)
          .getBody();
      String id = claims.getSubject();

      return new UsernamePasswordAuthenticationToken(id, null, Collections.emptyList());
    } catch (JwtException e) {
      return null;
    }
  }
}
