package com.tourist.app.entity.dto;

import java.util.List;

import com.tourist.app.database.EntityBase;
import com.tourist.app.database.users.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class UserDTO implements EntityBase<Integer> { 
  private Integer id;
  @NonNull
  private Boolean admin;
  @NonNull
  private TouristDTO tourist;
  @NonNull
  private String password;

  public static UserDTO userToDto(User entity) {
    var res = new UserDTO();
    
    res.setId(entity.getId());
    res.setAdmin(entity.getAdmin());
    res.setPassword(entity.getPassword());
    res.setTourist(TouristDTO.touristToDto(entity.getTourist()));

    return res;
  }

  public static User dtoToUser(UserDTO dto) {
    var res = new User();

    res.setId(dto.getId());
    res.setAdmin(dto.getAdmin());
    res.setPassword(dto.getPassword());
    res.setTourist(TouristDTO.dtoToTourist(dto.getTourist()));

    return res;
  }

  public static List<UserDTO> toDtoList(List<User> list) {
    return list.stream().map(UserDTO::userToDto).toList();
  }
}
