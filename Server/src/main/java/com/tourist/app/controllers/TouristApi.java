package com.tourist.app.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
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

import com.tourist.app.database.tourists.Tourist;
import com.tourist.app.entity.PageResponse;
import com.tourist.app.entity.TokenAndTourist;
import com.tourist.app.entity.TokenResponse;
import com.tourist.app.entity.dto.TouristDTO;
import com.tourist.app.entity.dto.TripDTO;
import com.tourist.app.services.database.ITouristService;
import com.tourist.app.services.database.ITripService;
import com.tourist.app.services.database.IUserService;
import com.tourist.app.utils.TokenGenerator;

/**
 * It's a REST controller that handles the requests to the /tourist endpoint
 */
@RestController
@RequestMapping("tourist")
public class TouristApi {
  @Autowired
  private ITouristService tService;
  @Autowired
  private ITripService tripsService;
  @Autowired
  private IUserService uService;

  /**
   * This function returns a list of TouristDTO, which are the result of converting the
   * Tourist returned by the TouristService
   * 
   * @param page the page number, starting from 1
   * @param name The name of the user or users.
   * @return A list of TouristDTOs
   */
  @GetMapping
  public PageResponse<TouristDTO> getAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
      @RequestParam(name = "name", required = false) String name) {
    Page<Tourist> res; 
    if (name != null) {
      res = tService.findByName(name, page - 1);
    } else {
      res = tService.getAll(page - 1);
    }

    return new PageResponse<>(TouristDTO.toDtoList(res.getContent()), res.getTotalElements(), res.getTotalPages());
  }

  /**
   * "Get all tourists born between startDate and endDate, and return a page of them."
   * 
   * @param page the page number, default value is 1
   * @param startDate the start date of the date range
   * @param endDate the end date of the time space. If it's not specified, it will be set to the start
   * date plus one day.
   * @return A list of TouristDTOs
   */
  @GetMapping("/born")
  public PageResponse<TouristDTO> getByBornDate(@RequestParam(name = "page", defaultValue = "1") Integer page,
      @RequestParam(name = "start") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
      @RequestParam(name = "end", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {
    if (endDate == null) {
      endDate = startDate.plusDays(1);
    }

    var res = tService.getByBornDateTimeSpace(startDate, endDate, page - 1);

    return new PageResponse<>(TouristDTO.toDtoList(res.getContent()), res.getTotalElements(), res.getTotalPages());
  }

  /**
   * It updates a tourist's information. The tourist only can update his self
   * 
   * @param auth Authentication object that contains the user's credentials.
   * @param t TouristDTO
   * @return A map with the token and the updated tourist.
   */
  @PutMapping
  public ResponseEntity<TokenAndTourist> updateTourist(final Authentication auth, @RequestBody TouristDTO t) {
    final var turist = tService.getByIdCard(auth.getName());

    if (turist.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    t.setId(turist.get().getId());

    Tourist updated; 
    try {
      updated = tService.update(TouristDTO.dtoToTourist(t));
    } catch(DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    
    final var user = uService.findByIdCard(updated.getIdCard());
    if (user.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    var token = TokenGenerator.createToken(updated.getIdCard()); 
    var tkRes = new TokenResponse(token, updated.getIdCard(), "Bearer ", user.get().getAdmin());
    
    var res = new TokenAndTourist(tkRes, TouristDTO.touristToDto(updated));

    return ResponseEntity.ok(res);
  }

  /**
   * It returns a list of trips for a given tourist
   * 
   * @param id the id of the tourist
   * @param page the page number to return (defaults to 1)
   * @return A list of trips that the tourist has taken.
   */
  @GetMapping("{userid}")
  public PageResponse<TripDTO> getTirpsHistory(@PathVariable("userid") Integer id,
      @RequestParam(name = "page", defaultValue = "1") Integer page) {
    final var res = tripsService.getTripsFromTourist(id, page - 1);

    return new PageResponse<>(TripDTO.toDtoList(res.getContent()), res.getTotalElements(), res.getTotalPages());
  }

  /**
   * It deletes a tourist.
   * The tourist only can delete itself if it's not an admin
   * 
   * @param auth Authentication object that contains the user's credentials.
   * @param tourist the tourist to be deleted
   */
  @DeleteMapping
  public void deleteTourist(final Authentication auth, @RequestBody(required = false) final TouristDTO tourist) {
    Boolean isAdmin = false;
    Tourist toDelete;

    if (tourist != null && tourist.getId() != null) {
      for (var i : auth.getAuthorities()) {
        if (i.getAuthority().equals("ROLE_ADMIN")) {
          isAdmin = true;
          break;
        }
      }
      if (Boolean.FALSE.equals(isAdmin)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
      toDelete = TouristDTO.dtoToTourist(tourist);
    } else {
      var userTourist = tService.getByIdCard(auth.getName());
      if (userTourist.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      toDelete = userTourist.get();
    }

    tService.delete(toDelete); 
  }
}
