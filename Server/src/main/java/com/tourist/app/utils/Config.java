package com.tourist.app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Config {

  private static Environment env;

  @Autowired
  public void setEnv(Environment env) {
    Config.env = env;
  }
  
  public static Integer getPort() {
    return env.getProperty("server.port", Integer.class);
  }

  public static String getSecret() {
    return env.getProperty("token.secret", String.class);
  }

  public static Long getExpirationTime() {
    return env.getProperty("token.expiraton", Long.class);
  } 
  
}