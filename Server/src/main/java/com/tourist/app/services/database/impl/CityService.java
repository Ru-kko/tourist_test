package com.tourist.app.services.database.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tourist.app.database.cities.City;
import com.tourist.app.database.cities.ICitiesRepository;
import com.tourist.app.entity.PageResponse;
import com.tourist.app.services.database.ICityService;

@Service
class CityService implements ICityService {
  @Autowired
  private ICitiesRepository repo;

  @Override
  public PageResponse<City> getAll(Integer page) {
    Pageable content = PageRequest.of(page, 50);
    Page<City> res = repo.findAll(content);

    return new PageResponse<>(res.getContent(), res.getTotalElements(), res.getTotalPages());
  }

  @Override
  public Optional<City> getById(Integer id) {
    return repo.findById(id);
  }

  @Override
  public City save(City value) {
    if (value == null || value.getId() != null)
      return null;
    return repo.save(value);
  }

  @Override
  public City update(City toUpdate) {
    if (toUpdate.getId() == null)
      return null;

    Optional<City> oldData = repo.findById(toUpdate.getId());

    if (oldData.isEmpty())
      return null;

    toUpdate.setTrips(oldData.get().getTrips());

    if (toUpdate.getName() == null)
      toUpdate.setName(oldData.get().getName());

    if (toUpdate.getPopulation() == null)
      toUpdate.setPopulation(oldData.get().getPopulation());

    if (toUpdate.getMostTuristicPlace() == null)
      toUpdate.setMostTuristicPlace(oldData.get().getMostTuristicPlace());

    if (toUpdate.getMostReserverdHotel() == null)
      toUpdate.setMostReserverdHotel(oldData.get().getMostReserverdHotel());

    return repo.save(toUpdate);
  }

  @Override
  public void deleteByID(Integer id) {
    repo.deleteById(id);
  }

  @Override
  public void delete(City value) {
    repo.delete(value);
  }

  @Override
  public PageResponse<City> findByName(String name, Integer page) {
    Pageable content = PageRequest.of(page, 50);
    var res = repo.findByPartialName(name, content);

    return new PageResponse<>(res.getContent(), res.getTotalElements(), res.getTotalPages());
  }
}
