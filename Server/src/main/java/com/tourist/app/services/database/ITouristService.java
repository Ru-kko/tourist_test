package com.tourist.app.services.database;

import java.time.LocalDate;
import java.util.Optional;

import com.tourist.app.dataBase.tourists.Tourist;
import com.tourist.app.entity.PageResponse;

public interface ITouristService extends IDatabaBaseService<Integer, Tourist> {
  public PageResponse<Tourist> findByName(String name, Integer page);

  public Optional<Tourist> getByIdCard(String idCard);

  public PageResponse<Tourist> getByBornDateTimeSpace(LocalDate start, LocalDate end, Integer page);
}
