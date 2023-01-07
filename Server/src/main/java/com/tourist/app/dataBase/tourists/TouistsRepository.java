package com.tourist.app.dataBase.tourists;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tourist.app.dataBase.BaseRepository;

@Repository
public class TouistsRepository extends BaseRepository<Integer, Tourist>{
  @Autowired
  protected ITouristsRepository repo;

  public Page<Tourist> getAll(Pageable page) {
    return repo.findAll(page);
  }

  public Page<Tourist> findByName(String name, Pageable page) {
    return repo.findByPartialName(name, page);
  }

  public Optional<Tourist> getByIdCard(String idCard){
    return repo.findFristByIdCard(idCard);
  }

  public Page<Tourist> getByBornDateTimeSpace(Calendar start, Calendar end, Pageable page) {
    return repo.findAllByBornDateBetween(start, end, page);
  }

  @Override
  protected ITouristsRepository getRepo() {
    return repo;
  }
}