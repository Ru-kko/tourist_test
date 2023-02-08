package com.tourist.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tourist.app.entity.GenerateUserDetails;
import com.tourist.app.services.database.IUserService;
@Service
public class UserDetailService implements UserDetailsService {
  @Autowired
  private IUserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userService.findByIdCard(username);

    if (user.isEmpty())
      throw new UsernameNotFoundException(username);

    return new GenerateUserDetails(user.get());
  }
}
