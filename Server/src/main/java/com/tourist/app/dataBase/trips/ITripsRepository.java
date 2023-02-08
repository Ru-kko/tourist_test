package com.tourist.app.dataBase.trips;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITripsRepository extends CrudRepository<Trip, Integer> {
  @Query(value= "SELECT COUNT(*) FROM Trips WHERE cityId = ?1 AND startDate = DATE(?2)", nativeQuery = true)
  public Long countTripsInACityAtSameTime(Integer cityID, LocalDate startDate);

  public Page<Trip> findByTouristId(Integer touristId, Pageable page);

  public Page<Trip> findByCityId(Integer cityId, Pageable page);

  public Page<Trip> findAll(Pageable page);
}
