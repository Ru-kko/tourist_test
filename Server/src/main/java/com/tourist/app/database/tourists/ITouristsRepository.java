package com.tourist.app.database.tourists;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITouristsRepository extends CrudRepository<Tourist, Integer> {

  /**
   * It searches for a tourist by name or last name.
   * 
   * @param partial the partial name to search for
   * @param page the page number, starting from 0
   * @return A Page of Tourists
   */
  @Query(value = "SELECT * FROM Tourists WHERE LOWER(name) LIKE %:name% OR LOWER(lastName) LIKE %:name% OR LOWER(CONCAT(name, lastName)) LIKE %:name%", 
    countQuery = "SELECT COUNT(*) FROM Tourists WHERE LOWER(name) LIKE %:name% OR LOWER(lastName) LIKE %:name% OR LOWER(CONCAT(name, lastName)) LIKE %:name%", 
    nativeQuery = true)
  public Page<Tourist> findByPartialName(@Param("name") String partial, Pageable page);

  public Optional<Tourist> findFristByIdCard(String idCard);

  public Page<Tourist> findAll(Pageable page);

  /**
   * Find all Tourists whose bornDate is between bornDatestart and bornDateEnd, and return them in a
   * Page.
   * 
   * @param bornDatestart The start of the date range to search for.
   * @param bornDateEnd The end of the date range.
   * @param page the page number, starting from 0
   * @return A page of Tourists
   */
  public Page<Tourist> findAllByBornDateBetween(LocalDate bornDatestart, LocalDate bornDateEnd, Pageable page);
}