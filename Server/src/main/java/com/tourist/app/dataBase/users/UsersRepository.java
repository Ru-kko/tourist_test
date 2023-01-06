package com.tourist.app.dataBase.users;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tourist.app.dataBase.BaseRepository;

@Repository
public class UsersRepository extends BaseRepository<Integer, User> {
  @Autowired
  protected IUsersRepository repo;

  public Optional<User> findByTouristIdCard(String idCard) {
    return repo.selecFromTouristIdCard(idCard);
  } 

  @Override
  protected IUsersRepository getRepo() {
    return repo;
  }
}
