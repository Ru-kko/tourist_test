package com.tourist.app.services.database;

import java.time.LocalDate;

import com.tourist.app.dataBase.trips.Trip;
import com.tourist.app.entity.PageResponse;

public interface ITripService extends IDatabaBaseService<Integer, Trip> {

  public PageResponse<Trip> getTripsFromTourist(Integer touristId, Integer page);

  public PageResponse<Trip> getTripsFromCity(Integer cityId, Integer page);

  public Long countTouristAtSameDay(LocalDate startDate, Integer cityId);
}
