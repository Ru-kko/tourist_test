package com.tourist.app.entity;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tourist.app.dataBase.users.User;

public class GenerateUserDetails implements UserDetails {

  private User user;

  public GenerateUserDetails(User user) {
    this.user = user;
  }

  private class UserAuthoority implements GrantedAuthority {
    private Boolean isAdmin;

    public UserAuthoority(Boolean isAdmin) {
      this.isAdmin = isAdmin;
    }

    @Override
    public String getAuthority() {
      if (this.isAdmin) return "ROLE_ADMIN";
      return "ROLE_USER";
    }

  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    var res = new ArrayList<UserAuthoority>(1);

    res.add(new UserAuthoority(this.user.getAdmin()));
    
    return res;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getTourist().getFullName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
