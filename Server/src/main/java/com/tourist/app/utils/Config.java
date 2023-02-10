package com.tourist.app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * It's a Spring component that reads the properties from the application.properties file and exposes
 * them as static methods
 */
@Component
public class Config {

  private static Environment env;

  @Autowired
  private void env(Environment environment) {
    Config.env = environment;
  }

  /**
   * This function returns the maximum number of tourists allowed in a city at same day.
   * This value will be given form the trip.maximum-tourist property from the application.properties file.
   * 
   * @return The maximum number of tourists allowed.
   */
  public static Long getMaxTourist() {
    return env.getProperty("trip.maximun-tourists", Long.class);
  }

  /**
   * If the server.port property is defined in the environment, return it as an Integer, otherwise
   * return null.
   * 
   * @return The port number of the server.
   */
  public static Integer getPort() {
    return env.getProperty("server.port", Integer.class);
  }

  /**
   * If the token.secret property is set in the environment, return it.
   * 
   * @return The value of the token.secret property from the application.properties file.
   */
  public static String getSecret() {
    return env.getProperty("token.secret", String.class);
  }
  /**
   * If the token.expiration property is set in the environment, return it.
   * 
   * @return The value of the token.expiration property.
   */
  public static Long getExpirationTime() {
    return env.getProperty("token.expiraton", Long.class);
  } 
  /**
   * If the security.cors property is set in the enviromen, return it otherwise return false
   * @return Boolean 
   */
  public static Boolean getCors() {
    var res = env.getProperty("security.cors", Boolean.class);
    return res != null && res; 
  }
}