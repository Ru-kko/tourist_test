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

@Entity
@Table(name = "Tourists")
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

  @OneToMany(mappedBy = "tourist", orphanRemoval = true, cascade = CascadeType.DETACH)
  @JsonIgnoreProperties("tourist")
  private List<Trip> trips = new ArrayList<>();

  public Tourist() {
  }
  
  public Tourist(LocalDate bornDate, String name,String lastName, String idCard, Integer travelFrequency, Double travelBudget) {
    this.bornDate = bornDate;
    this.idCard = idCard;
    this.travelFrequency = travelFrequency;
    this.travelBudget = travelBudget;
    this.lastName = lastName;
    this.name = name;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public List<Trip> getTrips() {
    return trips;
  }

  public void setTrips(List<Trip> trips) {
    this.trips = trips;
  }

  public User getAccount() {
    return account;
  }

  public void setAccount(User account) {
    this.account = account;
  }

  public Integer getTravelFrequency() {
    return travelFrequency;
  }

  public void setTravelFrequency(Integer travelFrequency) {
    this.travelFrequency = travelFrequency;
  }

  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }

  public Double getTravelBudget() {
    return travelBudget;
  }

  public void setTravelBudget(Double travelBudget) {
    this.travelBudget = travelBudget;
  }

  public LocalDate getBornDate() {
    return bornDate;
  }

  public void setBornDate(LocalDate bornDate) {
    this.bornDate = bornDate;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }
}
