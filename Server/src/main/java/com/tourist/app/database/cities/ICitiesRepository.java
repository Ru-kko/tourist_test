package com.tourist.app.database.cities;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICitiesRepository extends CrudRepository<City, Integer> {
 
  /**
   * Find all cities whose name contains the string partialName, and return them as a Page of City
   * objects.
   * 
   * @param partialName The name of the city to search for.
   * @param page The page number to return.
   * @return A Page of City objects.
   */
  @Query(value = "SELECT * FROM Cities WHERE LOWER(name) LIKE %:partialName%", countQuery = "SELECT COUNT(*) FROM Citites WHERE LOWER(name) LIKE %:partialName%", nativeQuery = true)
  public Page<City> findByPartialName(@Param("partialName") String partialName, Pageable page);

  public Page<City> findAll(Pageable page);
}
