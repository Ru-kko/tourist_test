package com.tourist.app.dataBase.tourists;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tourist.app.dataBase.EntityBase;
import com.tourist.app.dataBase.trips.Trip;
import com.tourist.app.dataBase.users.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Tourists")
@Data
@NoArgsConstructor
public class Tourist implements EntityBase<Integer> {
  @Id
  @Column(name = "touristId")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Temporal(TemporalType.DATE)
  private LocalDate bornDate;

  
  private String name;
  private String lastName;
  

  
  private Integer travelFrequency;
  private Double travelBudget;
  @Column(name = "idCard", unique = true)
  private String idCard;

  @OneToOne(mappedBy = "tourist", orphanRemoval = true)
  @JsonIgnoreProperties("tourist")
  private User account;

  @OneToMany(mappedBy = "tourist", orphanRemoval = true, cascade = CascadeType.REMOVE)
  @JsonIgnoreProperties("tourist")
  private List<Trip> trips = new ArrayList<>();
  
  public Tourist(LocalDate bornDate, String name,String lastName, String idCard, Integer travelFrequency, Double travelBudget) {
    this.bornDate = bornDate;
    this.idCard = idCard;
    this.travelFrequency = travelFrequency;
    this.travelBudget = travelBudget;
    this.lastName = lastName;
    this.name = name;
  }
}
