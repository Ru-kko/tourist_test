package com.tourist.app.controllers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tourist.app.dataBase.tourists.Tourist;
import com.tourist.app.dataBase.trips.Trip;
import com.tourist.app.entity.PageResponse;
import com.tourist.app.entity.TokenResponse;
import com.tourist.app.services.db.Tourists;
import com.tourist.app.services.db.Trips;
import com.tourist.app.services.db.Users;
import com.tourist.app.utils.TokenGenerator;

@RestController
@RequestMapping("tourist")
public class TouristApi {
  @Autowired
  private Tourists tService;
  @Autowired
  private Trips tripsService;
  @Autowired
  private Users uService;

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
      @RequestParam(name = "start") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
      @RequestParam(name = "end", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {
    if (endDate == null) {
      endDate = startDate;
      endDate.plusDays(1);
    }

    return tService.getByBornDateTimeSpace(startDate, endDate, page - 1);
  }

  @PutMapping
  public ResponseEntity<Map<String, Object>> updateTourist(final Authentication auth, @RequestBody Tourist t) {
    t.setTrips(Collections.emptyList());
    t.setAccount(null);
    final var turist = tService.getByIdCard(auth.getName());
    t.setId(turist.get().getId());

    Tourist updated; 
    try {
      updated = tService.update(t);
    } catch(DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    
    final var user = uService.getByIdCard(updated.getIdCard());
    if (user.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    var token = TokenGenerator.createToken(updated.getIdCard()); 
    var tkRes = new TokenResponse(token, updated.getIdCard(), "Bearer ", user.get().getAdmin());
    
    var res = new HashMap<String, Object>();
    res.put("token", tkRes);
    res.put("updated", updated);

    return ResponseEntity.ok(res);
  }

  @GetMapping("{userid}")
  public PageResponse<Trip> getTirpsHistory(@PathVariable("userid") Integer id,
      @RequestParam(name = "page", defaultValue = "1") Integer page) {
    return tripsService.getTripsFromTourist(id, page - 1);
  }

  @DeleteMapping
  public void deleteTourist(final Authentication auth, @RequestBody(required = false) final Tourist tourist) {
    Boolean isAdmin = false;

    if (tourist != null && tourist.getId() != null) {
      for (var i : auth.getAuthorities()) {
        if (i.getAuthority() == "ROLE_ADMIN") {
          isAdmin = true;
          break;
        }
      }
      if (!isAdmin) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
      tService.delete(tourist);
    }
    final var todelete = tService.getByIdCard(auth.getName());
    if (todelete.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    tService.delete(todelete.get()); 
  }
}
