package com.tourist.app.dataBase.trips;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.tourist.app.dataBase.cities.CitiesRepository;
import com.tourist.app.dataBase.cities.City;
import com.tourist.app.dataBase.tourists.TouistsRepository;
import com.tourist.app.dataBase.tourists.Tourist;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class TripTest {
  @Autowired
  private TripsRepository repo;

  @Autowired
  private TouistsRepository tRepo;
  @Autowired
  private CitiesRepository cRepo;

  private City[] cities = new City[2];
  private Tourist[] tourists = new Tourist[6];

  private Trip[] trips = new Trip[5];
  private LocalDate date;

  @BeforeAll
  void initialize() {
    date = LocalDate.of(2023, 1, 1);


    for (int i = 0; i < tourists.length; i++) {
      Tourist toSave = new Tourist(date, "touris number " + i, Integer.toString(i * 12), i,
          (i * 5 / 3) * 1.0d);
      tourists[i] = tRepo.save(toSave);
    }
    for (int i = 0; i < cities.length; i++) {
      City toSave = new City("city no " + i, i * 1231, "nan", "nan");
      cities[i] = cRepo.save(toSave);
    }
  }

  @Test
  public void should_save_trips() {
    for (int i = 0; i < tourists.length - 1; i++) {
      Trip trip = new Trip();

      trip.setCity(cities[0]);
      trip.setTourist(tourists[i]);
      trip.setStartDate(date);

      trips[i] = repo.save(trip);
    } 
    
    for (Trip t : trips) {
      Optional<Trip> finded = repo.getById(t.getId());
      assertTrue(finded.isPresent());
      assertEquals(t.getCity().getId(), finded.get().getCity().getId());
    }

    // Should find trips in a city at same day
    Long count = repo.countTouristAtSameDay(trips[0].getStartDate(), cities[0].getId());
    assertEquals(tourists.length - 1, count);

    // Should find trips from an user
    Page<Trip> tourstTrips = repo.getTripsFromTourist(tourists[0].getId(), PageRequest.of(0, 50));

    assertEquals(1, tourstTrips.getNumber());
  }

  @Test
  public void should_delete_a_trip() {
    Trip newTrip = new Trip();

    newTrip.setCity(cities[1]);
    newTrip.setTourist(tourists[5]);
    newTrip.setStartDate(date);

    Trip saved = repo.save(newTrip);
    Optional<Trip> find = repo.getById(saved.getId());
    repo.delete(saved);
    Optional<Trip> delete = repo.getById(saved.getId());

    assertNotNull(saved.getId());
    assertTrue(find.isPresent());
    assertEquals(saved.getId(), find.get().getId());
    assertFalse(delete.isPresent());
  }

  @AfterAll
  public void delete() {
    for (Trip t : trips) {
      repo.delete(t);
    }
    for (City c : cities) {
      cRepo.delete(c);
    }
    for (Tourist t : tourists) {
      tRepo.delete(t);
    }
  }
}
