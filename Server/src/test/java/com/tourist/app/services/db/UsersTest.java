package com.tourist.app.services.db;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Calendar;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.tourist.app.dataBase.tourists.Tourist;
import com.tourist.app.dataBase.users.User;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UsersTest {

  @Autowired
  private Tourists tService;

  @Autowired
  private Users service;

  private Tourist[] tData = new Tourist[2];
  private User[] testData = new User[2];
  
  @BeforeAll
  void initializeData() {
    Calendar bornDate = Calendar.getInstance();
    bornDate.set(2001, 10, 2);

    tData[0] = tService.save(new Tourist(bornDate, "name 1", "1234", 1, 1.5d));
    tData[1] = tService.save(new Tourist(bornDate, "name 2", "123", 1, 1.5d));
  }

  @Test
  void testSave() {
    User frist = new User();
    frist.setAdmin(false);
    frist.setPassword("password");
    frist.setTourist(tData[0]);

    testData[0] = service.save(frist);

    assertNotNull(testData[0]);
    assertNotNull(testData[0].getId());

    User second = new User();
    second.setAdmin(false);
    second.setPassword("password");
    second.setTourist(tData[0]);

    assertThrows(DataIntegrityViolationException.class, () -> service.save(second));

    second.setTourist(tData[1]);

    testData[1] = service.save(second);
    
    assertNotNull(testData[1]);
    assertNotNull(testData[1].getId());
    assertNotNull(testData[1].getTourist());

  }

  @AfterAll
  void destroy() {
    service.delete(testData[0]);
    service.delete(testData[1]);
    
    tService.delete(tData[0]);
    tService.delete(tData[1]);

  }
}
