package com.tourist.app.entity;

import com.tourist.app.entity.dto.TouristDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenAndTourist {
  private TokenResponse token;
  private TouristDTO tourist;
}
