package com.tourist.app.services.database.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tourist.app.database.trips.ITripsRepository;
import com.tourist.app.database.trips.Trip;
import com.tourist.app.services.database.ITripService;
import com.tourist.app.utils.Config;

@Service
public class TripService implements ITripService {
  @Autowired
  private ITripsRepository repo;

  @Override
  public Page<Trip> getAll(Integer page) {
    Pageable content = PageRequest.of(page, 50);
    return repo.findAll(content);
  }

  @Override
  public Optional<Trip> getById(Integer id) {
    return repo.findById(id);
  }

  @Override
  public Trip save(Trip trip) {
    if (trip == null ||
        trip.getCity() == null ||
        trip.getCity().getId() == null ||
        trip.getTourist() == null ||
        trip.getTourist().getId() == null)
      return null;

    Long count = countTouristAtSameDay(trip.getStartDate(), trip.getCity().getId());

    if (count >= Config.getMaxTourist())
      return null;

    return repo.save(trip);
  }

  @Override
  public Trip update(Trip toUpdate) {
    if (toUpdate == null || toUpdate.getId() == null)
      return null;

    Optional<Trip> oldData = repo.findById(toUpdate.getId());

    if (oldData.isEmpty())
      return null;

    if (toUpdate.getCity() == null)
      toUpdate.setCity(oldData.get().getCity());

    if (toUpdate.getTourist() == null)
      toUpdate.setTourist(oldData.get().getTourist());

    if (toUpdate.getStartDate() == null)
      toUpdate.setStartDate(oldData.get().getStartDate());

    return repo.save(toUpdate);

  }

  @Override
  public void deleteByID(Integer id) {
    repo.deleteById(id);
  }

  @Override
  public void delete(Trip value) {
    repo.delete(value);
  }

  @Override
  public Page<Trip> getTripsFromTourist(Integer touristId, Integer page) {
    return repo.findByTouristId(touristId, PageRequest.of(page, 50));
  }

  @Override
  public Page<Trip> getTripsFromCity(Integer cityId, Integer page) {
    return repo.findByCityId(cityId, PageRequest.of(page, 50));
  }

  @Override
  public Long countTouristAtSameDay(LocalDate startDate, Integer cityId) {
    return repo.countTripsInACityAtSameTime(cityId, startDate);
  }

}
