package com.tourist.app.controllers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;
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
import org.springframework.web.server.ResponseStatusException;

import com.tourist.app.database.cities.City;
import com.tourist.app.database.tourists.Tourist;
import com.tourist.app.database.trips.Trip;
import com.tourist.app.entity.PageResponse;
import com.tourist.app.entity.dto.CityDTO;
import com.tourist.app.entity.dto.TripDTO;
import com.tourist.app.services.database.ICityService;
import com.tourist.app.services.database.ITouristService;
import com.tourist.app.services.database.ITripService;

/**
 * It's a REST controller that handles requests to the /city endpoint
 */
@RestController
@RequestMapping("/city")
public class CitiesApi {
  @Autowired
  private ICityService cService;
  @Autowired
  private ITripService tService;
  @Autowired
  private ITouristService touristService;

  /**
   * It takes in a page number and a name, and returns a list of cities that match the name, or all
   * cities if no name is provided
   * 
   * @param pageNum The page number to be returned.
   * @param name The name of the parameter.
   * @return A PageResponse object with a list of CityDTO objects, the total number of elements, and
   * the total number of pages.
   */
  @GetMapping
  public PageResponse<CityDTO> getAll(@RequestParam(name = "page", defaultValue = "1") Integer pageNum,
      @RequestParam(name = "name", required = false) String name) {
    if (name != null) {
      Page<City> res = cService.findByName(name, pageNum - 1);
      return new PageResponse<>(CityDTO.toDtoList(res.getContent()), res.getTotalElements(), res.getTotalPages());
    }

    Page<City> res = cService.getAll(pageNum - 1);
    return new PageResponse<>(CityDTO.toDtoList(res.getContent()), res.getTotalElements(), res.getTotalPages());
  }

  /**
   * It takes a CityDTO, converts it to a City, saves it to the database, and returns the DTO
   * 
   * @param cityDt The CityDTO that will be converted to a City object and saved to the
   * database.
   * @return ResponseEntity<CityDTO>
   */
  @PostMapping
  public ResponseEntity<CityDTO> createCity(@RequestBody CityDTO cityDt) {
    if (cityDt == null)
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    var city = CityDTO.dtoToCity(cityDt);

    city.setTrips(Collections.emptyList());

    City save = cService.save(city);

    if (save == null || save.getId() == null)
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    return ResponseEntity.ok(CityDTO.cityToDto(save));
  }

  /**
   * It updates a city.
   * 
   * @param cityDt The city that we want to update.
   * @return A  is being returned.
   */
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

  /**
   * It takes the city id and the date as parameters, and returns a TripDTO object
   * 
   * @param auth Authentication object that contains the user's credentials.
   * @param id the id of the city to reserve
   * @param date The date of the trip
   * @return A TripDTO object
   */
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

  /**
   * Get a list of trips from a city, with pagination.
   * 
   * @param id the id of the city
   * @param page The page number to return.
   * @return A list of trips from a city.
   */
  @GetMapping("/{cityid}")
  public PageResponse<TripDTO> getCityHistory(@PathVariable("cityid") Integer id,
      @RequestParam(name = "page", defaultValue = "1") Integer page) {
    var res = tService.getTripsFromCity(id, page - 1);
    return new PageResponse<>(TripDTO.toDtoList(res.getContent()), res.getTotalElements(), res.getTotalPages());
  }


  /**
   * If the user is not the owner of the trip, then the user is not allowed to delete the trip
   * 
   * @param auth Authentication object
   * @param id the id of the trip to be deleted
   */
  @DeleteMapping("/trip/{tripId}")
  public void deleteTrip(final Authentication auth, @PathVariable("tripId") Integer id) {
    var user = touristService.getByIdCard(auth.getName()).orElse(null);
    var trip = tService.getById(id).orElse(null);

    if (user == null || trip == null)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    if (!Objects.equals(trip.getTourist().getId(), user.getId()))
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    
    tService.deleteByID(id);
  }

  /**
   * It deletes a city from the database.
   * 
   * @param city The city object that will be deleted.
   */
  @DeleteMapping
  public void delete(@RequestBody City city) {
    cService.delete(city);
  }
}
