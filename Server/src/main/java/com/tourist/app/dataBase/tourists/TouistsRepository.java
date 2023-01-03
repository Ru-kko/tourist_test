package com.tourist.app.dataBase.tourists;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tourist.app.dataBase.BaseRepository;

@Repository
public class TouistsRepository extends BaseRepository<Integer, Tourist>{
  @Autowired
  protected ITouristsRepository repo;

  public List<Tourist> findByName(String name) {
    return repo.findByPartialName(name);
  }

  public Optional<Tourist> getByIdCard(String idCard){
    return repo.findFristByIdCard(idCard);
  }

  public List<Tourist> getByBornDateTimeSpace(Date start, Date end) {
    return repo.findAllByBornDateBetween(start, end);
  }

  @Override
  protected ITouristsRepository getRepo() {
    return repo;
  }
}