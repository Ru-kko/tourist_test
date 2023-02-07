package com.tourist.app.dataBase.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tourist.app.dataBase.EntityBase;
import com.tourist.app.dataBase.tourists.Tourist;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@Data
public class User implements EntityBase<Integer> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "userId")
  private Integer id;

  @Column(nullable = false)
  private String password;
  @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private Boolean admin;

  @OneToOne(optional = false, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "tourist", referencedColumnName = "touristId")
  @JsonIgnoreProperties("account")
  private Tourist tourist;
}
