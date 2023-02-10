package com.tourist.app.services.database;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tourist.app.database.tourists.Tourist;

public interface ITouristService extends IDatabaBaseService<Integer, Tourist> {
  public Page<Tourist> findByName(String name, Integer page);

  public Optional<Tourist> getByIdCard(String idCard);

  public Page<Tourist> getByBornDateTimeSpace(LocalDate start, LocalDate end, Integer page);
}
