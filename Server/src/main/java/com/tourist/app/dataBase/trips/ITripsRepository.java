package com.tourist.app.dataBase.trips;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

interface ITripsRepository extends CrudRepository<Trip, Integer> {
  @Query(value= "SELECT COUNT(*) FROM Trips WHERE cityId = ?1 AND startDate = DATE(?2)", nativeQuery = true)
  public Long countTripsInACityAtSameTime(Integer cityID, Date startDate);

  public List<Trip> findByTouristId(Integer touristId);
}
