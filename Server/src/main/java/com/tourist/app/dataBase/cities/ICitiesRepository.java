package com.tourist.app.dataBase.cities;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface ICitiesRepository extends CrudRepository<City, Integer> {

  @Query(value = "SELECT * FROM Cities WHERE LOWER(name) LIKE %:partialName%", nativeQuery = true)
  public List<City> findByPartialName(@Param("partialName") String partialName);
}
