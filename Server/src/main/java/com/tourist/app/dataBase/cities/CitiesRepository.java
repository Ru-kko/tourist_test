package com.tourist.app.dataBase.cities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tourist.app.dataBase.BaseRepository;

@Repository
public class CitiesRepository extends BaseRepository<Integer, City> {

  @Autowired
  protected ICitiesRepository repo;

  public Page<City> findByName(String name, Pageable page) {
    return repo.findByPartialName(name, page);
  }

  public Page<City> getAll(Pageable page) {
    return repo.findAll(page);
  }

  @Override
  protected ICitiesRepository getRepo() {
    return repo;
  }
}
