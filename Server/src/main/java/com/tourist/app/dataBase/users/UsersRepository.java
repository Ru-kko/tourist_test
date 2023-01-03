package com.tourist.app.dataBase.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tourist.app.dataBase.BaseRepository;

@Repository
public class UsersRepository extends BaseRepository<Integer, User> {
  @Autowired
  protected IUsersRepository repo;

  @Override
  protected IUsersRepository getRepo() {
    return repo;
  }
}
