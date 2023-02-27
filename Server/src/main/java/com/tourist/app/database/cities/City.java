package com.tourist.app.database.cities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tourist.app.database.EntityBase;
import com.tourist.app.database.trips.Trip;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "Cities")
@Data
@NoArgsConstructor
public class City implements EntityBase<Integer>, Serializable {
  @Id
  @Column(name = "cityId")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String name;
  private Integer population;
  private String mostTuristicPlace;
  private String mostReserverdHotel;

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "city", orphanRemoval = true)
  @JsonIgnoreProperties("city")
  private List<Trip> trips;

  public City(String name, Integer population, String mostTuristicPlace, String mostReserverdHotel) {
    this.name = name;
    this.population = population;
    this.mostTuristicPlace = mostTuristicPlace;
    this.mostReserverdHotel = mostReserverdHotel;
  }
}
