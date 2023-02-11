package com.tourist.app.entity;

import lombok.Data;

/**
 * It's a simple POJO that contains two fields, cardId and password, and getters and setters for each
 */
@Data
public class Credentials {
  private String cardId;
  private String password; 
}
