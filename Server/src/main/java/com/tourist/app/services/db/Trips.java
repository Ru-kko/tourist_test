package com.tourist.app.services.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourist.app.dataBase.BaseRepository;
import com.tourist.app.dataBase.trips.Trip;
import com.tourist.app.dataBase.trips.TripsRepository;
import com.tourist.app.services.DatabaseService;
import com.tourist.app.utils.Config;

@Service
public class Trips extends DatabaseService<Integer, Trip> {

  @Autowired
  private TripsRepository repo;

  @Override
  public Trip update(Trip toUpdate) {
    if (toUpdate.getId() == null)
      return null;

    Optional<Trip> oldData = repo.getById(toUpdate.getId());

    if (oldData.isEmpty())
      return null;

    if (toUpdate.getCity() == null)
      toUpdate.setCity(oldData.get().getCity());

    if (toUpdate.getTourist() == null)
      toUpdate.setTourist(oldData.get().getTourist());

    if (toUpdate.getStartDate() == null)
      toUpdate.setStartDate(oldData.get().getStartDate());

    return repo.update(toUpdate);
  }

  public Trip save(Trip trip) {

    if (trip == null ||
        trip.getCity() == null ||
        trip.getCity().getId() == null ||
        trip.getTourist() == null ||
        trip.getTourist().getId() == null)
      return null;

    Long count = countTouristAtSameDay(trip.getStartDate(), trip.getCity().getId());

    if (count > Config.getMaxTourist())
      return null;

    return super.save(trip);

  }

  public Long countTouristAtSameDay(Date startDate, Integer cityId) {
    return repo.countTouristAtSameDay(startDate, cityId);
  }

  public List<Trip> getTripsFromTourist(Integer touristId) {
    return repo.getTripsFromTourist(touristId);
  }

  @Override
  protected BaseRepository<Integer, Trip> getRepo() {
    return this.repo;
  }

}
