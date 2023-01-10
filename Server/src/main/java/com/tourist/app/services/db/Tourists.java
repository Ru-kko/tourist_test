package com.tourist.app.services.db;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tourist.app.dataBase.BaseRepository;
import com.tourist.app.dataBase.tourists.TouistsRepository;
import com.tourist.app.dataBase.tourists.Tourist;
import com.tourist.app.entity.PageResponse;
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

  public PageResponse<Tourist> findByName(String name, Integer page) {
    Pageable conten = PageRequest.of(page, 50);
    Page<Tourist> res = repo.findByName(name, conten);

    return new PageResponse<>(cleanSecrets(res.getContent()), res.getTotalElements(), res.getTotalPages());
  }

  public PageResponse<Tourist> getAll(Integer page) {
    Pageable content = PageRequest.of(page, 50);
    Page<Tourist> res = repo.getAll(content);

    return new PageResponse<>(cleanSecrets(res.getContent()), res.getTotalElements(), res.getTotalPages());
  }

  public Optional<Tourist> getByIdCard(String idCard) {
    return repo.getByIdCard(idCard);
  }

  public PageResponse<Tourist> getByBornDateTimeSpace(LocalDate start, LocalDate end, Integer page) {

    Page<Tourist> res = repo.getByBornDateTimeSpace(start, end, PageRequest.of(page, 50));

    return new PageResponse<>(cleanSecrets(res.getContent()), res.getTotalElements(), res.getTotalPages());
  }

  private List<Tourist> cleanSecrets(List<Tourist> initial) {
    return initial.stream().map((data) -> {
      data.setAccount(null);
      data.setTrips(null);
      return data;
    }).toList();
  }

  @Override
  protected BaseRepository<Integer, Tourist> getRepo() {
    return this.repo;
  }
}
