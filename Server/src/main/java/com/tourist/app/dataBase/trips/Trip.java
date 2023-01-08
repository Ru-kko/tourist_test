package com.tourist.app.dataBase.trips;

import java.util.Date;

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

@Entity
@Table(name = "trips")
public class Trip implements EntityBase<Integer> {
  @Id
  @Column(name = "tripId")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Temporal(TemporalType.DATE)
  @Column(name = "startDate", nullable = true)
  private Date startDate;

  @ManyToOne
  @JoinColumn(name = "touristId", nullable = false)
  @JsonIgnoreProperties({ "trips", "account" })
  private Tourist tourist;

  @ManyToOne
  @JoinColumn(name = "cityId", nullable = false)
  @JsonIgnoreProperties("trips")
  private City city;

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public Tourist getTourist() {
    return tourist;
  }

  public void setTourist(Tourist tourist) {
    this.tourist = tourist;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
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
