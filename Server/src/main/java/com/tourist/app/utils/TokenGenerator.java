package com.tourist.app.utils;

import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenGenerator {
  private Claims claims;

  public TokenGenerator(String token) {
    this.claims = Jwts.parserBuilder()
        .setSigningKey(Config.getSecret().getBytes())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public static String createToken(String cardId) {
    Date expDate = new Date(System.currentTimeMillis() + Config.getExpirationTime());

    return Jwts.builder()
        .setSubject(cardId)
        .setExpiration(expDate)
        .signWith(Keys.hmacShaKeyFor(Config.getSecret().getBytes()))
        .compact();
  }

  public UsernamePasswordAuthenticationToken getAuth(UserDetails details) {
    try {
      String id = claims.getSubject();

      return new UsernamePasswordAuthenticationToken(id, null, details.getAuthorities());
    } catch (JwtException e) {
      return null;
    }
  }

  public String GetUsername() {
    return claims.getSubject();
  }
}
