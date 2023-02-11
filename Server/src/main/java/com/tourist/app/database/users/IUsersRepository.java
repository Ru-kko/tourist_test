package com.tourist.app.database.users;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersRepository  extends CrudRepository<User, Integer>{

  /**
   * It selects the user from the tourist table where the idCard is equal to the idCard passed in.
   * 
   * @param idCard the idCard of the tourist
   * @return A list of all the users that have a tourist with the idCard.
   */
  @Query(value = "SELECT u.* FROM Users u INNER JOIN Tourists t ON t.touristId=u.tourist WHERE t.idCard= ?1", nativeQuery = true)
  public Optional<User> selecFromTouristIdCard(String idCard);

  public  Page<User> findAll(Pageable page);
}
