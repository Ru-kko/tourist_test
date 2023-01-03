package com.tourist.app.dataBase.cities;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tourist.app.dataBase.BaseRepository;

@Repository
public class CitiesRepository extends BaseRepository<Integer, City> {

  @Autowired
  protected ICitiesRepository repo;

  public List<City> findByName(String name) {
    return repo.findByPartialName(name);
  }

  @Override
  protected ICitiesRepository getRepo() {
    return repo;
  }
}
