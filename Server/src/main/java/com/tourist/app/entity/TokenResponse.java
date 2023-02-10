package com.tourist.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * It's a Eentity that holds the authorization token and user information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
  private String token;
  private String cardId;
  private String tokenType;
  private Boolean admin;
}
