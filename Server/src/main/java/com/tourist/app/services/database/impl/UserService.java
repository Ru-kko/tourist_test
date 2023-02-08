package com.tourist.app.services.database.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tourist.app.dataBase.users.IUsersRepository;
import com.tourist.app.dataBase.users.User;
import com.tourist.app.entity.PageResponse;
import com.tourist.app.services.database.IUserService;

@Service
class UserService implements IUserService {
  @Autowired
  private IUsersRepository repo;

  @Override
  public PageResponse<User> getAll(Integer page) {
    var content = PageRequest.of(page, 50);
    var res = repo.findAll(content);
    return new PageResponse<>(res.getContent(), res.getTotalElements(), res.getTotalPages());
  }

  @Override
  public Optional<User> getById(Integer id) {
    return repo.findById(id);
  }

  @Override
  public User save(User value) {
    if (value == null || value.getId() != null)
      return null;
    return repo.save(value);
  }

  @Override
  public User update(User toUpdate) {
    if (toUpdate == null || toUpdate.getId() == null)
      return null;

    Optional<User> oldData = repo.findById(toUpdate.getId());

    if (oldData.isEmpty())
      return null;

    if (toUpdate.getAdmin() == null)
      toUpdate.setAdmin(oldData.get().getAdmin());

    if (toUpdate.getPassword() == null)
      toUpdate.setPassword(oldData.get().getPassword());

    if (toUpdate.getTourist() == null)
      toUpdate.setTourist(oldData.get().getTourist());

    return repo.save(toUpdate);  
  }

  @Override
  public void deleteByID(Integer id) {
    repo.deleteById(id);
  }

  @Override
  public void delete(User value) {
    repo.delete(value);
  }

  @Override
  public Optional<User> findByIdCard(String idCard) {  
    return repo.selecFromTouristIdCard(idCard);
  }
}
