package com.tourist.app.dataBase.users;

import org.springframework.data.repository.CrudRepository;

interface IUsersRepository  extends CrudRepository<User, Integer>{
}
