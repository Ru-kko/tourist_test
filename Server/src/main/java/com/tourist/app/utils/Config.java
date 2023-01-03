package com.tourist.app.utils;

import org.springframework.beans.factory.annotation.Value;

public class Config {
  @Value("${token.expiration}")
  private static Long eXpirationTime;

  @Value("${token.secret}")
  private static String secret;

  @Value("${server.port}")
  private static Integer port;
  
  public static Integer getPort() {
    return port;
  }

  public static String getSecret() {
    return secret;
  }

  public static Long getExpirationTime() {
    return eXpirationTime;
  } 
  
}