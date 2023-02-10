package com.tourist.app.services.database;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.tourist.app.database.trips.Trip;

/**
 * A trip service interface that extends the IDatabaBaseService interface.
 * 
 * *see also*
 * @see IDatabaBaseService
 */
public interface ITripService extends IDatabaBaseService<Integer, Trip> {

  /**
   * Get all trips from a tourist, with pagination.
   * 
   * @param touristId The id of the tourist whose trips you want to get.
   * @param page The page number to return.
   * @return A page of trips that the tourist has been on.
   */
  public Page<Trip> getTripsFromTourist(Integer touristId, Integer page);

  /**
   * Get all trips from a city, paginated.
   * 
   * @param cityId The id of the city you want to get trips from.
   * @param page The page number to return.
   * @return A page of trips from a city.
   */
  public Page<Trip> getTripsFromCity(Integer cityId, Integer page);

  /**
   * Count the number of tourists who are going to the same city on the same day.
   * 
   * @param startDate the date of the tour
   * @param cityId The id of the city
   * @return The number of tourists who are in the same city on the same day.
   */
  public Long countTouristAtSameDay(LocalDate startDate, Integer cityId);


  /**
   * If the trip is valid, and the number of tourists in the city on the same day is less than the
   * maximum allowed, then save the trip
   * 
   * @param trip the trip to be saved
   * @return A trip object, it will be null if the trip couldn't save
   */
  @Override
  public Trip save(Trip value);
}
