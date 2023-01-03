package com.tourist.app.services.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tourist.app.dataBase.cities.City;

@SpringBootTest
public class CitiesTest {
  @Autowired
  private Cities service;

  @Test
  void testUpdate() {
    City cBase = new City("Base name", 1000, "will not update", "base hotel");

    cBase = service.save(cBase);

    assertNotNull(cBase.getId());

    City changed = new City("changed", 100, null, "second hotel");
    changed.setId(cBase.getId());

    City returned = service.update(changed);


    
    assertEquals(returned.getId(), cBase.getId());
    assertEquals(cBase.getMostTuristicPlace(), returned.getMostTuristicPlace());
    assertNotEquals(cBase.getName(), returned.getName());
    
    service.delete(returned);
  }
}
