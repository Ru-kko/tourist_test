package com.tourist.app.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tourist.app.dataBase.cities.City;
import com.tourist.app.entity.PageResponse;
import com.tourist.app.services.db.Cities;

@RestController
@RequestMapping("/city")
public class CitiesApi {
  @Autowired
  private Cities cService;

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

  @DeleteMapping
  public void delete(@RequestBody City city) {
    cService.delete(city);

  }
}
