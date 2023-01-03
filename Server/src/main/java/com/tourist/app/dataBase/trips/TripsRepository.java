package com.tourist.app.dataBase.trips;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tourist.app.dataBase.BaseRepository;

@Repository
public class TripsRepository extends BaseRepository<Integer, Trip> {

  @Autowired
  protected ITripsRepository repo;

  public Long countTouristAtSameDay(Date startDate, Integer cityId) {
    return repo.countTripsInACityAtSameTime(cityId, startDate);
  }

  public List<Trip> getTripsFromTourist(Integer touristId) {
    return repo.findByTouristId(touristId);
  } 

  @Override
  protected ITripsRepository getRepo() {
    return repo;
  }
}
