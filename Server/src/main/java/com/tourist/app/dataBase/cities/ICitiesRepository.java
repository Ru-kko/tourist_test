package com.tourist.app.dataBase.cities;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface ICitiesRepository extends CrudRepository<City, Integer> {

  @Query(value = "SELECT * FROM Cities WHERE LOWER(name) LIKE %:partialName%", countQuery = "SELECT COUNT(*) FROM Citites WHERE LOWER(name) LIKE %:partialName%", nativeQuery = true)
  public Page<City> findByPartialName(@Param("partialName") String partialName, Pageable page);

  public Page<City> findAll(Pageable page);
}
