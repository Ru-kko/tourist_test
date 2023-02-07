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
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "Cities")
@Data
@NoArgsConstructor
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
}
