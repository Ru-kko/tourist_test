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

  /**
   * It maps a User entity to DTO
   * 
   * @param entity The entity to map.
   * @return A DTO object
   */
  public static UserDTO userToDto(User entity) {
    var res = new UserDTO();
    
    res.setId(entity.getId());
    res.setAdmin(entity.getAdmin());
    res.setPassword(entity.getPassword());
    res.setTourist(TouristDTO.touristToDto(entity.getTourist()));

    return res;
  }

  
  /**
   * It takes a DTO object and maps to a User
   * 
   * @param dto The DTO object to be mapped to a User.
   * @return A User
   */
  public static User dtoToUser(UserDTO dto) {
    var res = new User();

    res.setId(dto.getId());
    res.setAdmin(dto.getAdmin());
    res.setPassword(dto.getPassword());
    res.setTourist(TouristDTO.dtoToTourist(dto.getTourist()));

    return res;
  }

  /**
   * Convert a list of Users objects to a list of DTO.
   * 
   * @param list The list of objects to be converted to DTOs.
   * @return A list of DTOs.
   */
  public static List<UserDTO> toDtoList(List<User> list) {
    return list.stream().map(UserDTO::userToDto).toList();
  }
}
