package com.tourist.app.services.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tourist.app.dataBase.cities.City;
import com.tourist.app.dataBase.tourists.Tourist;
import com.tourist.app.dataBase.trips.Trip;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class TripsTest {

  @Autowired
  private ITripService service;
  @Autowired
  private ICityService cService;
  @Autowired
  private ITouristService tService;

  private City cData;
  private Tourist[] tData = new Tourist[6];
  private Trip[] tripsData = new Trip[6];

  private LocalDate tripDate;

  @BeforeAll
  void initializeData() {
    cData = new City("Test city", 1000, "nan", "nan");
    cData = cService.save(cData);

    tripDate = LocalDate.of(2023, Month.JANUARY, 1);
  

    for (int i = 0; i < tData.length; i++) {
      LocalDate bornDate = LocalDate.of(2003, Month.APRIL, i + 1 * 2 );

      tData[i] = new Tourist(bornDate, "Tourist n " + i, " foo ", Integer.toString(i * 123), i, i * Math.E * i);

      tData[i] = tService.save(tData[i]);
    }
  }

  @Test
  void saveTest() {
    for (int i = 0; i < tData.length - 1; i++) {
      tripsData[i] = new Trip();

      tripsData[i].setCity(cData);
      tripsData[i].setTourist(tData[i]);
      tripsData[i].setStartDate(tripDate);

      tripsData[i] = service.save(tripsData[i]);

      assertNotNull(tripsData[i].getId());
    }

    Long count = service.countTouristAtSameDay(tripDate, cData.getId());
    assertEquals(5, count);
    
    tripsData[tripsData.length-1] = service.save(tripsData[tripsData.length - 1]);
    assertNull(tripsData[tripsData.length -1]);
    
  }
  

  @AfterAll
  void destroy() {
    for (Trip i : tripsData) {
      if (i != null)
        service.delete(i);
    }
    for (Tourist i : tData) {
      tService.deleteByID(i.getId());
    }
    cService.deleteByID(cData.getId());
  }
}
