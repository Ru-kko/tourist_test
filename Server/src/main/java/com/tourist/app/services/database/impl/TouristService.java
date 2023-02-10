package com.tourist.app.services.database.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tourist.app.database.tourists.ITouristsRepository;
import com.tourist.app.database.tourists.Tourist;
import com.tourist.app.services.database.ITouristService;

@Service
class TouristService implements ITouristService {
  @Autowired
  private ITouristsRepository repo;

  @Override
  public Page<Tourist> findByName(String name, Integer page) {
    Pageable conten = PageRequest.of(page, 50);
    return repo.findByPartialName(name, conten);
  }

  @Override
  public Page<Tourist> getAll(Integer page) {
    Pageable content = PageRequest.of(page, 50);
    return repo.findAll(content);
  }

  @Override
  public Optional<Tourist> getById(Integer id) {
    return repo.findById(id);
  }

  @Override
  public Tourist save(Tourist value) {
    if (value == null || value.getId() != null)
      return null;
    return repo.save(value);
  }

  @Override
  public Tourist update(Tourist toUpdate) {
    if (toUpdate == null || toUpdate.getId() == null)
      return null;

    Optional<Tourist> oldData = repo.findById(toUpdate.getId());

    if (oldData.isEmpty())
      return null;

    toUpdate.setTrips(oldData.get().getTrips());

    if (toUpdate.getBornDate() == null)
      toUpdate.setBornDate(oldData.get().getBornDate());

    if (toUpdate.getLastName() == null)
      toUpdate.setLastName(oldData.get().getLastName());

    if (toUpdate.getName() == null)
      toUpdate.setName(oldData.get().getName());
    if (toUpdate.getTravelFrequency() == null)
      toUpdate.setTravelFrequency(oldData.get().getTravelFrequency());

    if (toUpdate.getIdCard() == null)
      toUpdate.setIdCard(oldData.get().getIdCard());

    if (toUpdate.getTravelBudget() == null)
      toUpdate.setTravelBudget(oldData.get().getTravelBudget());

    return repo.save(toUpdate);
  }

  @Override
  public void deleteByID(Integer id) {
    repo.deleteById(id);
  }

  @Override
  public void delete(Tourist value) {
    repo.delete(value);

  }

  @Override
  public Optional<Tourist> getByIdCard(String idCard) {
    return repo.findFristByIdCard(idCard);
  }

  @Override
  public Page<Tourist> getByBornDateTimeSpace(LocalDate start, LocalDate end, Integer page) {
    return  repo.findAllByBornDateBetween(start, end, PageRequest.of(page, 50));
  }
}
