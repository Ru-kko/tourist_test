package com.tourist.app.dataBase.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


interface IUsersRepository  extends CrudRepository<User, Integer>{

  @Query(value = "SELECT u.* FROM Users u INNER JOIN Tourists t ON t.touristId=u.tourist WHERE t.idCard= ?1", nativeQuery = true)
  public Optional<User> selecFromTouristIdCard(String idCard);
}
