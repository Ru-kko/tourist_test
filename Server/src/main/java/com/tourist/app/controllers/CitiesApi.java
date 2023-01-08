package com.tourist.app.controllers;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.tourist.app.dataBase.cities.City;
import com.tourist.app.dataBase.tourists.Tourist;
import com.tourist.app.dataBase.trips.Trip;
import com.tourist.app.entity.PageResponse;
import com.tourist.app.services.db.Cities;
import com.tourist.app.services.db.Tourists;
import com.tourist.app.services.db.Trips;

@RestController
@RequestMapping("/city")
public class CitiesApi {
  @Autowired
  private Cities cService;
  @Autowired
  private Trips tService;
  @Autowired
  private Tourists touristService;

  @GetMapping
  public PageResponse<City> getAll(@RequestParam(name = "page", defaultValue = "1") Integer pageNum,
      @RequestParam(name = "name", required = false) String name) {
    if (name != null) {
      return cService.findByName(name, pageNum - 1);
    }
    return cService.getAll(pageNum - 1);
  }

  @PostMapping
  public ResponseEntity<City> createCity(@RequestBody City city) {
    if (city == null) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    city.setTrips(Collections.emptyList());

    City save = cService.save(city);

    if (save == null || save.getId() == null) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    return ResponseEntity.ok(city);
  }

  @PutMapping
  public ResponseEntity<City> updateCity(@RequestBody City city) {
    city.setTrips(Collections.emptyList());

    City updated = cService.update(city);
    if (updated == null) {
      return new ResponseEntity<City>(updated, HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.ok(city);
  }

  @PostMapping("/{cityid}")
  public ResponseEntity<Trip> reserve(Authentication auth, @PathVariable("cityid") Integer id,
      @RequestParam(name = "day") @DateTimeFormat(iso = ISO.DATE) Date date) {
    Optional<City> find = cService.getById(id);
    Optional<Tourist> tourist = touristService.getByIdCard(auth.getName());

    if (find.isEmpty() || tourist.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    Trip newTrip = new Trip();
    newTrip.setCity(find.get());
    newTrip.setStartDate(date);
    newTrip.setTourist(tourist.get());

    Trip saved = tService.save(newTrip);

    if (saved == null)
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    return ResponseEntity.ok(saved);
  }

  @GetMapping("/{cityid}")
  public PageResponse<Trip> getCityHistory(@PathVariable("cityid") Integer id, @RequestParam(name = "page", defaultValue = "1") Integer page) {
    return tService.getTripsFromCity(id, page - 1);
  }

  @DeleteMapping
  public void delete(@RequestBody City city) {
    cService.delete(city);
  }
}
