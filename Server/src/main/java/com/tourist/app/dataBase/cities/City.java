package com.tourist.app.dataBase.cities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tourist.app.dataBase.EntityBase;
import com.tourist.app.dataBase.trips.Trip;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cities")
public class City implements EntityBase<Integer>{
  @Id
  @Column(name = "cityId")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private Integer population;
  private String mostTuristicPlace;
  private String mostReserverdHotel;

  @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "city")
  @JsonIgnoreProperties("city")
  private List<Trip> trips;

  public City(String name, Integer population, String mostTuristicPlace, String mostReserverdHotel) {
    this.name = name;
    this.population = population;
    this.mostTuristicPlace = mostTuristicPlace;
    this.mostReserverdHotel = mostReserverdHotel;
  }

  public City() {
  }

  public List<Trip> getTrips() {
    return trips;
  }

  public void setTrips(List<Trip> trips) {
    this.trips = trips;
  }

  public String getMostReserverdHotel() {
    return mostReserverdHotel;
  }

  public void setMostReserverdHotel(String mostReserverdHotel) {
    this.mostReserverdHotel = mostReserverdHotel;
  }

  public String getMostTuristicPlace() {
    return mostTuristicPlace;
  }

  public void setMostTuristicPlace(String mostTuristicPlace) {
    this.mostTuristicPlace = mostTuristicPlace;
  }

  public Integer getPopulation() {
    return population;
  }

  public void setPopulation(Integer population) {
    this.population = population;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
