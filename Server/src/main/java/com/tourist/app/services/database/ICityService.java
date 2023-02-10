package com.tourist.app.services.database;

import org.springframework.data.domain.Page;

import com.tourist.app.database.cities.City;

public interface ICityService extends IDatabaBaseService<Integer, City> {
  public Page<City> findByName(String name, Integer page);
}
