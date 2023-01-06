package com.tourist.app.dataBase.tourists;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class TouristTest {
  @Autowired
  private TouistsRepository repo;
  private Tourist[] testData = new Tourist[6];

  @BeforeAll
  void initialize() throws ParseException {
    Calendar date = Calendar.getInstance();

    date.set(2003, 2, 8);

    testData[0] = new Tourist(date, "jonh doe", "1234", 2, 10.00d);
    testData[1] = new Tourist(date, "jane doe", "12356", 2, 10.00d);
    testData[2] = new Tourist(date, "pepe perez", "123478", 2, 10.00d);
    testData[3] = new Tourist(date, "deletable", "123", 2, 10.00d);
    testData[4] = new Tourist(date, "upadable", "134", 2, 10.00d);

    for (int i = 0; i < testData.length; i++) {
      testData[i] = repo.save(testData[i]);
    }
  }

  @Test
  void should_add_a_tourist() throws ParseException {
    Calendar date = Calendar.getInstance();
    date.set(2005, 12, 14);
    Tourist newTourist = new Tourist(date, "foo", "123456789", 5, 20.00d);

    testData[5] = repo.save(newTourist);

    assertNotNull(testData[5]);
    assertNotNull(testData[5].getId());
  }

  @Test
  void should_delete_a_tourists() {
    Optional<Tourist> saved = repo.getById(testData[3].getId());
    repo.delete(testData[3]);
    Optional<Tourist> deleted = repo.getById(testData[3].getId());

    assertTrue(saved.isPresent());
    assertFalse(deleted.isPresent());
  }

  @Test
  void should_find_people_taht_contains_doe_in_name() {
    List<Tourist> find = repo.findByName("doe");

    assertTrue(find.size() >= 2);
  }

  @Test
  void should_dont_make_two_tourists_with_same_card_id() {
    Calendar date = Calendar.getInstance();

    date.set(2003, 2, 8);
    Tourist err = new Tourist(date, "frist ", "4321", 2, 10.00d);
    Tourist err2 = new Tourist(date, "second", "4321", 2, 10.00d);

    err = repo.save(err);
    assertThrows(DataIntegrityViolationException.class, () -> repo.save(err2));

    repo.delete(err);
  }

  @Test
  void should_change_values_of_a_tourist() {
    Tourist touristBase = testData[4];

    touristBase.setFullName("changed");

    Tourist updated = repo.update(touristBase);
    Optional<Tourist> findedChange = repo.getById(testData[4].getId());

    assertTrue(findedChange.isPresent());
    assertEquals("changed", updated.getFullName());
    assertEquals("changed", findedChange.get().getFullName());
  }

  @AfterAll
  void delete() {
    for (Tourist c : testData) {
      repo.delete(c);
    }
  }
}
