package com.tourist.app.database.trips;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITripsRepository extends JpaRepository<Trip, Integer> {
  /**
   * Count the number of trips in a city at the same day.
   * 
   * @param cityID The ID of the city that the trip is in.
   * @param startDate The date the trip starts
   * @return The number of trips in a city at the same time.
   */
  @Query(value= "SELECT COUNT(*) FROM Trips WHERE cityId = ?1 AND startDate = CONVERT(DATE, ?2)", nativeQuery = true)
  public Long countTripsInACityAtSameTime(Integer cityID, LocalDate startDate);

  public Page<Trip> findByTouristId(Integer touristId, Pageable page);

  public Page<Trip> findByCityId(Integer cityId, Pageable page);

}
