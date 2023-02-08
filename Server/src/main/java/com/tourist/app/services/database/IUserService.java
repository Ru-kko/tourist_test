package com.tourist.app.services.database;

import java.util.Optional;

import com.tourist.app.database.users.User;

public interface IUserService extends IDatabaBaseService<Integer, User>{
  public Optional<User> findByIdCard(String idCard);
}
