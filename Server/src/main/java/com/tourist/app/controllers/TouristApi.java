package com.tourist.app.controllers;

import java.util.Calendar;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tourist.app.dataBase.tourists.Tourist;
import com.tourist.app.dataBase.trips.Trip;
import com.tourist.app.entity.PageResponse;
import com.tourist.app.services.db.Tourists;
import com.tourist.app.services.db.Trips;

@RestController
@RequestMapping("tourist")
public class TouristApi {
  @Autowired
  private Tourists tService;
  @Autowired
  private Trips tripsService;

  @GetMapping
  public PageResponse<Tourist> getAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
      @RequestParam(name = "name", required = false) String name) {
    if (name != null) {
      return tService.findByName(name, page - 1);
    }
    return tService.getAll(page - 1);
  }

  @GetMapping("/born")
  public PageResponse<Tourist> getByBornDate(@RequestParam(name = "page", defaultValue = "1") Integer page,
      @RequestParam(name = "start") @DateTimeFormat(iso = ISO.DATE) Calendar startDate,
      @RequestParam(name = "end", required = false) @DateTimeFormat(iso = ISO.DATE) Calendar endDate) {
    if (endDate == null) {
      endDate = startDate;
      endDate.add(Calendar.DATE, 1);
    }

    return tService.getByBornDateTimeSpace(startDate, endDate, page - 1);
  }

  @PutMapping
  public ResponseEntity<Tourist> updateTourist(@RequestBody Tourist t) {
    if (t == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    t.setTrips(Collections.emptyList());
    t.setAccount(null);

    Tourist upated = tService.update(t);

    if (upated == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return ResponseEntity.ok(upated);
  }

  @GetMapping("{userid}")
  public PageResponse<Trip> getTirpsHistory(@PathVariable("userid") Integer id,
      @RequestParam(name = "page", defaultValue = "1") Integer page) {
    return tripsService.getTripsFromTourist(id, page - 1);
  }
}
