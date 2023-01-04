package com.tourist.app.services.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourist.app.dataBase.BaseRepository;
import com.tourist.app.dataBase.tourists.TouistsRepository;
import com.tourist.app.dataBase.tourists.Tourist;
import com.tourist.app.services.DatabaseService;

@Service
public class Tourists extends DatabaseService<Integer, Tourist> {
  @Autowired
  private TouistsRepository repo;

  @Override
  public Tourist update(Tourist toUpdate) {
    if (toUpdate.getId() == null)
      return null;

    Optional<Tourist> oldData = repo.getById(toUpdate.getId());

    if (oldData.isEmpty())
      return null;

    if (toUpdate.getBornDate() == null)
      toUpdate.setBornDate(oldData.get().getBornDate());

    if (toUpdate.getFullName() == null)
      toUpdate.setFullName(oldData.get().getFullName());

    if (toUpdate.getTravelFrequency() == null)
      toUpdate.setTravelFrequency(oldData.get().getTravelFrequency());

    if (toUpdate.getIdCard() == null)
      toUpdate.setIdCard(oldData.get().getIdCard());

    if (toUpdate.getTravelBudget() == null)
      toUpdate.setTravelBudget(oldData.get().getTravelBudget());

    return repo.update(toUpdate);
  }

  public List<Tourist> findByName(String name) {
    return repo.findByName(name);
  }

  public Optional<Tourist> getByIdCard(String idCard) {
    return repo.getByIdCard(idCard);
  }

  public List<Tourist> getByBornDateTimeSpace(Date start, Date end) {
    return repo.getByBornDateTimeSpace(start, end);
  }

  @Override
  protected BaseRepository<Integer, Tourist> getRepo() {
    return this.repo;
  }
}
