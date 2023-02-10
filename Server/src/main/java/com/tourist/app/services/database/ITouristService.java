package com.tourist.app.services.database;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tourist.app.database.tourists.Tourist;

/**
 * A tourist service interface that extends the IDatabaBaseService interface.
 * 
 * *see also*
 * @see IDatabaBaseService
 */
public interface ITouristService extends IDatabaBaseService<Integer, Tourist> {
  /**
   * Find all Tourists with contains part of the given name, and return them in a Page object.
   * 
   * @param name The name of the tourist.
   * @param page the page number, starting from 0
   * @return A Page of Tourists
   */
  public Page<Tourist> findByName(String name, Integer page);

  /**
   * Get a tourist by id card.
   * 
   * @param idCard The idCard of the tourist to be searched.
   * @return Optional<Tourist>
   */
  public Optional<Tourist> getByIdCard(String idCard);

  /**
   * "Get all tourists born between start and end, ordered by born date, and return the page number
   * page of the results."
   * 
   * @param start the start of the time space
   * @param end the end of the date range
   * @param page the page number, starting from 0.
   * @return A page of Tourists.
   * 
   * *see also*
   * @see com.tourist.app.database.tourists.ITouristsRepository
   */
  public Page<Tourist> getByBornDateTimeSpace(LocalDate start, LocalDate end, Integer page);
}
