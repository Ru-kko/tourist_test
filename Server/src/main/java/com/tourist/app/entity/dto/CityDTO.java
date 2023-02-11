package com.tourist.app.entity.dto;

import java.util.List;

import com.tourist.app.database.EntityBase;
import com.tourist.app.database.cities.City;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class CityDTO implements EntityBase<Integer> {
  private Integer id;
  @NonNull
  private String name;
  @NonNull
  private Integer population;
  @NonNull
  private String mostTuristicPlace;
  @NonNull
  private String mostReserverdHotel;

  /**
   * It maps a City entity to CityDTO
   * 
   * @param entity The entity to map.
   * @return A CityDTO object
   */
  public static CityDTO cityToDto(City entity) {
    var res = new CityDTO();
    
    res.setId(entity.getId());
    res.setName(entity.getName());
    res.setPopulation(entity.getPopulation());
    res.setMostReserverdHotel(entity.getMostReserverdHotel());
    res.setMostTuristicPlace(entity.getMostTuristicPlace());
    
    return res;
  }

  /**
   * It takes a CityDTO object and maps to a City object
   * 
   * @param dto The DTO object to be mapped to a City object.
   * @return A City object
   */
  public static City dtoToCity(CityDTO dto) {
    var res = new City();

    res.setId(dto.getId());
    res.setName(dto.getName());
    res.setPopulation(dto.getPopulation());
    res.setMostReserverdHotel(dto.getMostReserverdHotel());
    res.setMostTuristicPlace(dto.getMostTuristicPlace());

    return res;
  }

  /**
   * Convert a list of Cities to a list of CityDTO objects.
   * 
   * @param list The list of objects to be converted to DTOs.
   * @return A list of CityDTO objects.
   */
  public static List<CityDTO> toDtoList(List<City> list) {
    return list.stream().map(CityDTO::cityToDto).toList();
  }
}
