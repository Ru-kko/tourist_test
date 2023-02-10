package com.tourist.app.entity.dto;

import java.time.LocalDate;
import java.util.List;

import com.tourist.app.database.EntityBase;
import com.tourist.app.database.trips.Trip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class TripDTO implements EntityBase<Integer> {
  private Integer id;
  @NonNull
  private LocalDate startDate;
  @NonNull
  private TouristDTO tourist;
  @NonNull
  private CityDTO city;

  public static TripDTO tripToDto(Trip entity) {
    var res = new TripDTO();
 
    res.setId(entity.getId());
    res.setStartDate(entity.getStartDate());
    res.setTourist(TouristDTO.touristToDto(entity.getTourist()));
    res.setCity(CityDTO.cityToDto(entity.getCity()));

    return res;
  }

  public static Trip dtoToTrip(TripDTO dto) {
    var res = new Trip();

    res.setId(dto.getId());
    res.setStartDate(dto.getStartDate());
    res.setTourist(TouristDTO.dtoToTourist(dto.getTourist()));
    res.setCity(CityDTO.dtoToCity(dto.getCity()));

    return res;
  }

  public static List<TripDTO> toDtoList(List<Trip> list) {
    return list.stream().map(TripDTO::tripToDto).toList();
  }
}
