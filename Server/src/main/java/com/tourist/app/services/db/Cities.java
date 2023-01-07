package com.tourist.app.services.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tourist.app.dataBase.cities.CitiesRepository;
import com.tourist.app.dataBase.cities.City;
import com.tourist.app.entity.PageResponse;
import com.tourist.app.services.DatabaseService;

@Service
public class Cities extends DatabaseService<Integer, City> {
  @Autowired
  private CitiesRepository repo;

  @Override
  public City update(City toUpdate) {
    if (toUpdate.getId() == null)
      return null;

    Optional<City> oldData = repo.getById(toUpdate.getId());

    if (oldData.isEmpty())
      return null;

    if (toUpdate.getName() == null)
      toUpdate.setName(oldData.get().getName());

    if (toUpdate.getPopulation() == null)
      toUpdate.setPopulation(oldData.get().getPopulation());

    if (toUpdate.getMostTuristicPlace() == null)
      toUpdate.setMostTuristicPlace(oldData.get().getMostTuristicPlace());

    if (toUpdate.getMostReserverdHotel() == null)
      toUpdate.setMostReserverdHotel(oldData.get().getMostReserverdHotel());

    return repo.update(toUpdate);
  }

  public PageResponse<City> findByName(String name, Integer page) {
    Pageable content = PageRequest.of(page, 50);
    var res = repo.findByName(name, content);

    return new PageResponse<>(res.getContent(), res.getTotalElements(), res.getTotalPages());
  }

  public PageResponse<City> getAll(Integer page) {
    Pageable content = PageRequest.of(page, 50);
    Page<City> res = repo.getAll(content);

    return new PageResponse<>(res.getContent(), res.getTotalElements(), res.getTotalPages());
  }

  @Override
  protected CitiesRepository getRepo() {
    return this.repo;
  }
}
