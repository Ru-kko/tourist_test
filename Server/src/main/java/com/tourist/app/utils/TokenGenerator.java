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

  /**
   * Parse a token an get it's claims
   * @param token
   */
  public TokenGenerator(String token) {
    this.claims = Jwts.parserBuilder()
        .setSigningKey(Config.getSecret().getBytes())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * Create a token with the cardId as the subject, an expiration date, and sign it with the secret
   * key.
   * 
   * @param cardId The cardId of the user
   * @return A JWT token
   */
  public static String createToken(String cardId) {
    Date expDate = new Date(System.currentTimeMillis() + Config.getExpirationTime());

    return Jwts.builder()
        .setSubject(cardId)
        .setExpiration(expDate)
        .signWith(Keys.hmacShaKeyFor(Config.getSecret().getBytes()))
        .compact();
  }

  /**
   * If the token is valid, return a new UsernamePasswordAuthenticationToken with the user's id, null
   * password, and the authorities from the UserDetails object
   * 
   * @param details The user details object that contains the user's information.
   * @return A UsernamePasswordAuthenticationToken object.
   */
  public UsernamePasswordAuthenticationToken getAuth(UserDetails details) {
    try {
      String id = claims.getSubject();

      return new UsernamePasswordAuthenticationToken(id, null, details.getAuthorities());
    } catch (JwtException e) {
      return null;
    }
  }

  /**
   * It returns the username of the user.
   * the username is the cardId asociated to the user
   * 
   * @return The username of the user.
   */
  public String getUsername() {
    return claims.getSubject();
  }
}
