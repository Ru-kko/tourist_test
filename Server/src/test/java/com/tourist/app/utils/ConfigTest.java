package com.tourist.app.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigTest {
  @Test
  public void should_get_not_null_configuration() {
    Integer port = Config.getPort();
    Long exp = Config.getExpirationTime();
    Long maxTrips = Config.getMaxTourist();
    String secret = Config.getSecret();

    assertNotNull(maxTrips);
    assertNotNull(port);
    assertNotNull(secret);
    assertNotNull(exp);
  }
}
