package com.tourist.app.services.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Calendar;

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
  private Trips service;
  @Autowired
  private Cities cService;
  @Autowired
  private Tourists tService;

  private City cData;
  private Tourist[] tData = new Tourist[6];
  private Trip[] tripsData = new Trip[6];

  private Calendar tripDate;

  @BeforeAll
  void initializeData() {
    cData = new City("Test city", 1000, "nan", "nan");
    cData = cService.save(cData);

    tripDate = Calendar.getInstance();
    tripDate.set(2023, 0, 7);

    for (int i = 0; i < tData.length; i++) {
      Calendar bornDate = Calendar.getInstance();
      bornDate.set(2003, i, i * 2);

      tData[i] = new Tourist(bornDate, "Tourist n " + i, Integer.toString(i * 123), i, i * Math.E * i);

      tData[i] = tService.save(tData[i]);
    }
  }

  @Test
  void saveTest() {
    for (int i = 0; i < tData.length - 1; i++) {
      tripsData[i] = new Trip();

      tripsData[i].setCity(cData);
      tripsData[i].setTourist(tData[i]);
      tripsData[i].setStartDate(tripDate.getTime());

      tripsData[i] = service.save(tripsData[i]);

      assertNotNull(tripsData[i].getId());
    }

    Long count = service.countTouristAtSameDay(tripDate.getTime(), cData.getId());
    assertEquals(5, count);
    
    Trip shouldDontSave = service.save(tripsData[tripsData.length - 1]);

    assertNull(shouldDontSave);
    
  }
  

  @AfterAll
  void destroy() {
    for (Trip i : tripsData) {
      service.delete(i);
    }
    for (Tourist i : tData) {
      tService.deleteById(i.getId());
    }
    cService.deleteById(cData.getId());
  }
}
