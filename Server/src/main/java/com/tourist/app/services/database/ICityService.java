package com.tourist.app.services.database;

import org.springframework.data.domain.Page;

import com.tourist.app.database.cities.City;
/**
 * A city service interface that extends the IDatabaBaseService interface.
 * 
 * *see also*
 * @see IDatabaBaseService
 */
public interface ICityService extends IDatabaBaseService<Integer, City> {
  /**
   * Find all cities whose name catains the given text name, and return them in a pageable fashion.
   * 
   * @param name The part name of the city to search for.
   * @param page The page number to retrieve.
   * @return A Page object.
   */
  public Page<City> findByName(String name, Integer page);
}
