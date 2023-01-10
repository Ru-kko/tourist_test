package com.tourist.app.dataBase.trips;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tourist.app.dataBase.BaseRepository;

@Repository
public class TripsRepository extends BaseRepository<Integer, Trip> {

  @Autowired
  protected ITripsRepository repo;

  public Long countTouristAtSameDay(LocalDate startDate, Integer cityId) {
    return repo.countTripsInACityAtSameTime(cityId, startDate);
  }

  public Page<Trip> getTripsFromTourist(Integer touristId, Pageable page) {
    return repo.findByTouristId(touristId, page);
  } 

  public Page<Trip> getTripsFromCity(Integer cityId, Pageable page) {
    return repo.findByCityId(cityId, page);
  }

  @Override
  protected ITripsRepository getRepo() {
    return repo;
  }
}
