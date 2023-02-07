package com.tourist.app.dataBase.tourists;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface ITouristsRepository extends CrudRepository<Tourist, Integer> {

  @Query(value = "SELECT * FROM Tourists WHERE LOWER(name) LIKE %:name% OR LOWER(lastName) LIKE %:name% OR LOWER(CONCAT(name, lastName)) LIKE %:name%", 
    countQuery = "SELECT COUNT(*) FROM Tourists WHERE LOWER(name) LIKE %:name% OR LOWER(lastName) LIKE %:name% OR LOWER(CONCAT(name, lastName)) LIKE %:name%", 
    nativeQuery = true)
  public Page<Tourist> findByPartialName(@Param("name") String partial, Pageable page);

  public Optional<Tourist> findFristByIdCard(String idCard);

  public Page<Tourist> findAll(Pageable page);

  public Page<Tourist> findAllByBornDateBetween(LocalDate bornDatestart, LocalDate bornDateEnd, Pageable page);
}