package com.tourist.app.services.database;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.tourist.app.database.trips.Trip;

public interface ITripService extends IDatabaBaseService<Integer, Trip> {

  public Page<Trip> getTripsFromTourist(Integer touristId, Integer page);

  public Page<Trip> getTripsFromCity(Integer cityId, Integer page);

  public Long countTouristAtSameDay(LocalDate startDate, Integer cityId);
}
