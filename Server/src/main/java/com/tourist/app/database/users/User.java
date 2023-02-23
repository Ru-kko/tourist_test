package com.tourist.app.database.users;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tourist.app.database.EntityBase;
import com.tourist.app.database.tourists.Tourist;

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
public class User implements EntityBase<Integer>, Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "userId")
  private Integer id;

  @Column(nullable = false)
  private String password;
  @Column(nullable = false)
  private Boolean admin = false;

  @OneToOne(optional = false, cascade = CascadeType.MERGE)
  @JoinColumn(name = "tourist", referencedColumnName = "touristId")
  @JsonIgnoreProperties("account")
  private Tourist tourist;
}
