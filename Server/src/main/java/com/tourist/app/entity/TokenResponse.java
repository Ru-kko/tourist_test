package com.tourist.app.entity;

public class TokenResponse {
  private String token;
  private String cardId;
  private String tokenType;

  public TokenResponse() {
  }

  public TokenResponse(String token, String cardId, String tokenType) {
    this.token = token;
    this.cardId = cardId;
    this.tokenType = tokenType;
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
