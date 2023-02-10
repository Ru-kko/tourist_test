package com.tourist.app.services.database;

import java.util.Optional;

import com.tourist.app.database.users.IUsersRepository;
import com.tourist.app.database.users.User;

/**
 * A user service interface that extends the IDatabaBaseService interface.
 * 
 * *see also*
 * @see IDatabaBaseService
 */
public interface IUserService extends IDatabaBaseService<Integer, User>{
  /**
   * Find a user by id card, if found, return the user, otherwise return null.
   * 
   * @param idCard The idCard of the user to find.
   * @return Optional<User>
   * 
   * *see also*
   * @see IUsersRepository
   */
  public Optional<User> findByIdCard(String idCard);
}
