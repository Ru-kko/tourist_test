package com.tourist.app.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * It's a Spring component that reads the properties from the application.properties file and exposes
 * them as static methods
 */
@Component
public class Config {

  private static Long maxTourist;
  private static Integer port;
  private static String secret;
  private static Long expTime;
  private static Boolean cors;


  @Value("${trip.maximun-tourists}")
  void setMaxTourist(Long maxTourist) {
    Config.maxTourist = maxTourist;
  }

  @Value("${server.port}")
  void setPort(Integer port) {
    Config.port = port;
  }

  @Value("${token.secret}")
  void setSecret(String secret) {
    Config.secret = secret;
  }

  @Value("${token.expiration}")
  void setExpTime(Long expTime) {
    Config.expTime = expTime;
  }

  @Value("${security.cors}")
  void setCors(Boolean cors) {
    Config.cors = cors;
  } 


  /**
   * This function returns the maximum number of tourists allowed in a city at same day.
   * This value will be given form the trip.maximum-tourist property from the application.properties file.
   * 
   * @return The maximum number of tourists allowed.
   */
  public static Long getMaxTourist() {
    return Config.maxTourist;
  }

  /**
   * If the server.port property is defined in the environment, return it as an Integer, otherwise
   * return null.
   * 
   * @return The port number of the server.
   */
  public static Integer getPort() {
    return Config.port;
  }

  /**
   * If the token.secret property is set in the environment, return it.
   * 
   * @return The value of the token.secret property from the application.properties file.
   */
  public static String getSecret() {
    return Config.secret;
  }
  /**
   * If the token.expiration property is set in the environment, return it.
   * 
   * @return The value of the token.expiration property.
   */
  public static Long getExpirationTime() {
    return Config.expTime;
  } 
  /**
   * If the security.cors property is set in the enviromen, return it otherwise return false
   * @return Boolean 
   */
  public static Boolean getCors() {
    var res = Config.cors;
    return res != null && res; 
  }
}