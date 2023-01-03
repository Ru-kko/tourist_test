package com.tourist.app.dataBase.tourists;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface ITouristsRepository extends CrudRepository<Tourist, Integer> {

  @Query(value = "SELECT * FROM Tourists WHERE LOWER(fullName) LIKE %:name%", nativeQuery = true)
  public List<Tourist> findByPartialName(@Param("name") String partial);

  public Optional<Tourist> findFristByIdCard(String idCard);

  public List<Tourist> findAllByBornDateBetween(Date bornDatestart, Date bornDateEnd);
}