package com.tourist.app.entity;

public class TokenResponse {
  private String token;
  private String cardId;
  private String tokenType;
  private Boolean admin;

  public TokenResponse() {
  }

  public TokenResponse(String token, String cardId, String tokenType, Boolean admin) {
    this.token = token;
    this.cardId = cardId;
    this.tokenType = tokenType;
    this.admin = admin;
  }

  public Boolean getAdmin() {
    return admin;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getCardId() {
    return cardId;
  }

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

}
