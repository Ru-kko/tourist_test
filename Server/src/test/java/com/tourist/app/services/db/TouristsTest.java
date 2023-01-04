package com.tourist.app.services.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;

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
    Calendar date = Calendar.getInstance();
    Calendar date2 = Calendar.getInstance();

    date.set(2003, 2, 8);
    date2.set(2005, 12, 14);
    
    Tourist tBase = new Tourist(date, "jonh doe", "1234", 2, 10.00d);

    tBase = service.save(tBase);

    assertNotNull(tBase.getId());

    Tourist changed = new Tourist(date2, null, null, 2, 20.00d);

    changed.setId(tBase.getId());

    Tourist returned = service.update(changed);

    assertEquals(returned.getId(), tBase.getId());
    assertEquals(tBase.getFullName(), returned.getFullName());
    assertNotEquals(tBase.getTravelBudget(), returned.getTravelBudget());
    assertNotEquals(tBase.getBornDate().getTime().getTime(), returned.getBornDate().getTime().getTime());

    service.delete(returned);
  }
}

