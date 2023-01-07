package com.tourist.app.dataBase.cities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class CityTest {
  @Autowired
  private CitiesRepository repo;
  private City[] testData = new City[6];

  @BeforeAll
  void initialize() {
    testData[0] = new City("Cali", 75000, "plaza", "foo");
    testData[1] = new City("acalis", 50000, "not important", "foo");
    testData[2] = new City("focala", 50254, "", "");
    testData[3] = new City("deletable", 50254, "foo", "a");
    testData[4] = new City("Upadable",50205, "place", "bar");

    for (int i = 0; i < testData.length; i++) {
      testData[i] = repo.save(testData[i]);
    }
  }

  @Test
  void should_add_city() {
    City newCity = new City("Bogota", 600000, "plaza", "hiltlon");

    testData[5] = repo.save(newCity);

    assertNotNull(testData[5]);
    assertNotNull(testData[5].getId());
  }

  @Test
  void should_deletea_city() {
    Optional<City> saved = repo.getById(testData[3].getId());
    repo.delete(testData[3]);
    Optional<City> deleted = repo.getById(testData[3].getId());

    assertTrue(saved.isPresent());
    assertFalse(deleted.isPresent());
  }

  @Test
  void should_find_two_cities() { 
    var finded = repo.findByName("cal", PageRequest.of(0, 30));

    assertTrue(finded.getTotalElements() >= 3);
  }

  @Test
  void should_change_values_of_a_city(){
    City cityBase = new City();

    cityBase.setId(testData[4].getId());
    cityBase.setMostTuristicPlace("new");
    cityBase.setName("changedName");

    City updated = repo.update(cityBase);
    Optional<City> findedChange = repo.getById(testData[4].getId());
    
    assertTrue(findedChange.isPresent());
    assertEquals("new", updated.getMostTuristicPlace());
    assertEquals("new", findedChange.get().getMostTuristicPlace());
    assertEquals("changedName", updated.getName());
    assertEquals("changedName", findedChange.get().getName());
  }

  @AfterAll
  void delete() {
    for (City c : testData) {
      repo.delete(c);
    }
  }
}
