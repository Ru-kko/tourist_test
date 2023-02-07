package com.tourist.app.services.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tourist.app.dataBase.tourists.Tourist;

@SpringBootTest
public class TouristsTest {
  @Autowired
  private Tourists service;

  @Test
  void testUpdate() {
    LocalDate date = LocalDate.of(2023, 1, 1);
    LocalDate date2 = LocalDate.of(2003, 2, 8);

    Tourist tBase = new Tourist(date, "jonh", "doe", "45471", 2, 10.00d);

    tBase = service.save(tBase);

    assertNotNull(tBase.getId());

    Tourist changed = new Tourist(date2, null, null, null, 2, 20.00d);

    changed.setId(tBase.getId());

    Tourist returned = service.update(changed);

    assertEquals(returned.getId(), tBase.getId());
    assertEquals(tBase.getName(), returned.getName());
    assertNotEquals(tBase.getTravelBudget(), returned.getTravelBudget());
    assertNotEquals(tBase.getBornDate(), returned.getBornDate());

    service.delete(returned);
  }
}
