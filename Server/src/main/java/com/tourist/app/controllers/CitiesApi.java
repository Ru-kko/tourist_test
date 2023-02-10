package com.tourist.app.controllers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tourist.app.database.cities.City;
import com.tourist.app.database.tourists.Tourist;
import com.tourist.app.database.trips.Trip;
import com.tourist.app.entity.PageResponse;
import com.tourist.app.entity.dto.CityDTO;
import com.tourist.app.entity.dto.TripDTO;
import com.tourist.app.services.database.ICityService;
import com.tourist.app.services.database.ITouristService;
import com.tourist.app.services.database.ITripService;

@RestController
@RequestMapping("/city")
public class CitiesApi {
  @Autowired
  private ICityService cService;
  @Autowired
  private ITripService tService;
  @Autowired
  private ITouristService touristService;

  @GetMapping
  public PageResponse<CityDTO> getAll(@RequestParam(name = "page", defaultValue = "1") Integer pageNum,
      @RequestParam(name = "name", required = false) String name) {
    Page<City> res;
    if (name != null) {
      res = cService.findByName(name, pageNum - 1);
    } else {
      res = cService.getAll(pageNum - 1);
    }

    return new PageResponse<>(CityDTO.toDtoList(res.getContent()), res.getTotalElements(), res.getTotalPages());
  }

  @PostMapping
  public ResponseEntity<City> createCity(@RequestBody CityDTO cityDt) {
    if (cityDt == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    var city = CityDTO.dtoToCity(cityDt);

    city.setTrips(Collections.emptyList());

    City save = cService.save(city);

    if (save == null || save.getId() == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return ResponseEntity.ok(city);
  }

  @PutMapping
  public ResponseEntity<CityDTO> updateCity(@RequestBody CityDTO cityDt) {
    var city = CityDTO.dtoToCity(cityDt);

    city.setTrips(Collections.emptyList());

    City updated = cService.update(city);
    if (updated == null) {
      return new ResponseEntity<>(CityDTO.cityToDto(updated), HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.ok(CityDTO.cityToDto(updated));
  }

  @PostMapping("/{cityid}")
  public ResponseEntity<TripDTO> reserve(Authentication auth, @PathVariable("cityid") Integer id,
      @RequestParam(name = "day") @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
    Optional<City> find = cService.getById(id);
    Optional<Tourist> tourist = touristService.getByIdCard(auth.getName());

    if (find.isEmpty() || tourist.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Trip newTrip = new Trip();
    newTrip.setCity(find.get());
    newTrip.setStartDate(date);
    newTrip.setTourist(tourist.get());

    Trip saved = tService.save(newTrip);

    if (saved == null)
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    return ResponseEntity.ok(TripDTO.tripToDto(saved));
  }

  @GetMapping("/{cityid}")
  public PageResponse<TripDTO> getCityHistory(@PathVariable("cityid") Integer id,
      @RequestParam(name = "page", defaultValue = "1") Integer page) {
    var res = tService.getTripsFromCity(id, page - 1);
    return new PageResponse<>(TripDTO.toDtoList(res.getContent()), res.getTotalElements(), res.getTotalPages());
  }

  @DeleteMapping
  public void delete(@RequestBody City city) {
    cService.delete(city);
  }
}
