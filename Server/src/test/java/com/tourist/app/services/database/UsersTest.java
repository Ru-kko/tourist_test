package com.tourist.app.services.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.tourist.app.database.tourists.Tourist;
import com.tourist.app.database.users.User;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class UsersTest {

  @Autowired
  private ITouristService tService;

  @Autowired
  private IUserService service;

  private Tourist[] tData = new Tourist[3];
  private User[] testData = new User[3];

  @BeforeAll
  void initializeData() {
    LocalDate bornDate = LocalDate.of(2003, 2, 8);

    tData[0] = tService.save(new Tourist(bornDate, "name", "1", "1234545", 1, 1.5d));
    tData[1] = tService.save(new Tourist(bornDate, "name", "2", "123445", 1, 1.5d));
    tData[2] = tService.save(new Tourist(bornDate, "name", "3", "12345445", 1, 1.5d));
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

  @Test
  void should_find_by_tourist_idCard() {
    var newUser = new User();
    newUser.setAdmin(false);
    newUser.setPassword("secretpsw");
    newUser.setTourist(tData[2]);

    testData[2] = service.save(newUser);

    var finded = service.findByIdCard(tData[2].getIdCard());

    assertTrue(finded.isPresent());
    assertEquals(testData[2].getId(), finded.get().getId());
  }

  @AfterAll
  void destroy() {
    for (User i: testData) {
      if (i != null)
        service.delete(i);
    }
    for (Tourist i: tData) {
      if (i != null)
        tService.delete(i);
    }
  }
}
