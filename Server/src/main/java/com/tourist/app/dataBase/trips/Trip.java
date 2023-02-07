package com.tourist.app.dataBase.trips;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tourist.app.dataBase.EntityBase;
import com.tourist.app.dataBase.cities.City;
import com.tourist.app.dataBase.tourists.Tourist;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Trips")
@Data
@NoArgsConstructor
public class Trip implements EntityBase<Integer> {
  @Id
  @Column(name = "tripId")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Temporal(TemporalType.DATE)
  @Column(name = "startDate", nullable = true)
  private LocalDate startDate;

  @ManyToOne
  @JoinColumn(name = "touristId", nullable = false)
  @JsonIgnoreProperties({ "trips", "account" })
  private Tourist tourist;

  @ManyToOne
  @JoinColumn(name = "cityId", nullable = false)
  @JsonIgnoreProperties("trips")
  private City city;

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }
}
