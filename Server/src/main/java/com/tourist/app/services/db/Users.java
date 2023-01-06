package com.tourist.app.services.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourist.app.dataBase.BaseRepository;
import com.tourist.app.dataBase.users.User;
import com.tourist.app.dataBase.users.UsersRepository;
import com.tourist.app.services.DatabaseService;

@Service
public class Users extends DatabaseService<Integer, User> {
  @Autowired
  private UsersRepository repo;

  @Override
  public User update(User toUpdate) {
    if (toUpdate.getId() == null)
      return null;

    Optional<User> oldData = repo.getById(toUpdate.getId());

    if (oldData.isEmpty())
      return null;

    if (toUpdate.getAdmin() == null)
      toUpdate.setAdmin(oldData.get().getAdmin());
    
    if (toUpdate.getPassword() == null)
      toUpdate.setPassword(oldData.get().getPassword());

    if (toUpdate.getTourist() == null)
      toUpdate.setTourist(oldData.get().getTourist());

    return repo.update(toUpdate);
  }

  public Optional<User> getByIdCard(String idCard) {
    return repo.findByTouristIdCard(idCard);
  }

  @Override
  protected BaseRepository<Integer, User> getRepo() {
    return this.repo;
  }
}
