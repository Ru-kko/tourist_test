package com.tourist.app.entity.dto;

import java.time.LocalDate;
import java.util.List;

import com.tourist.app.database.EntityBase;
import com.tourist.app.database.tourists.Tourist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class TouristDTO implements EntityBase<Integer> {
  private Integer id;
  @NonNull
  private LocalDate bornDate;
  @NonNull
  private String name;
  @NonNull
  private String lastName;
  @NonNull
  private Integer travelFrequency;
  @NonNull
  private Double travelBudget;
  @NonNull
  private String idCard;

  /**
   * It maps a Tourist entity to DTO
   * 
   * @param entity The entity to map.
   * @return A DTO object
   */
  public static TouristDTO touristToDto(Tourist entity) {
    var res = new TouristDTO();
    
    res.setId(entity.getId());
    res.setName(entity.getName());
    res.setLastName(entity.getLastName());
    res.setBornDate(entity.getBornDate());
    res.setIdCard(entity.getIdCard());
    res.setTravelBudget(entity.getTravelBudget());
    res.setTravelFrequency(entity.getTravelFrequency());

    return res;
  }

  /**
   * It takes a DTO object and maps to a Tourist
   * 
   * @param dto The DTO object to be mapped to a Tourist.
   * @return A Tourist
   */
  public static Tourist dtoToTourist(TouristDTO dto) {
    var res = new Tourist();

    res.setId(dto.getId());
    res.setName(dto.getName());
    res.setLastName(dto.getLastName());
    res.setBornDate(dto.getBornDate());
    res.setIdCard(dto.getIdCard());
    res.setTravelBudget(dto.getTravelBudget());
    res.setTravelFrequency(dto.getTravelFrequency());

    return res;
  }

  /**
   * Convert a list of Tourists to a list of TouristDTO.
   * 
   * @param list The list of objects to be converted to DTOs.
   * @return A list of DTOs.
   */
  public static List<TouristDTO> toDtoList(List<Tourist> list) {
    return list.stream().map(TouristDTO::touristToDto).toList();
  }
}
