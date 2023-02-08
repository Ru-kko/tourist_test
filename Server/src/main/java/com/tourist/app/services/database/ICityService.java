package com.tourist.app.services.database;

import com.tourist.app.database.cities.City;
import com.tourist.app.entity.PageResponse;

public interface ICityService extends IDatabaBaseService<Integer, City> {
  public PageResponse<City> findByName(String name, Integer page);
}
