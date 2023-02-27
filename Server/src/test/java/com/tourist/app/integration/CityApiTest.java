package com.tourist.app.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.tourist.app.entity.PageResponse;
import com.tourist.app.entity.TokenResponse;
import com.tourist.app.entity.dto.CityDTO;
import com.tourist.app.entity.dto.TouristDTO;
import com.tourist.app.entity.dto.UserDTO;

@TestInstance(Lifecycle.PER_CLASS)
@Sql(statements = {
    "DELETE FROM Cities WHERE cityId = 124 OR cityId = 125 OR cityId = 126" }, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(statements = {
    "INSERT INTO Cities (cityId, name, population, mostTuristicPlace, mostReserverdHotel) " +
        "VALUES (124, 'Frist', 123, 'Foo', 'Bar')," +
        "(125, 'Second', 325, 'Fizz', 'Buzz')," +
        "(126, 'Third', 1452, 'Oof', 'Rab');" })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CityApiTest {
  @Autowired
  TestRestTemplate restTemplate;
  @Autowired
  JdbcTemplate jdbcTemplate;
  @LocalServerPort
  Integer port;
  String HOST;
  UserDTO me;
  HttpHeaders headers;

  @BeforeAll
  @SuppressWarnings("null")
  void initialize() {
    this.HOST = "http://localhost:" + this.port;
    this.headers = new HttpHeaders();

    var born = LocalDate.of(2000, 2, 12);
    var tourist = new TouristDTO(
        born,
        "jhon", "doe",
        23, 5000d,
        ThreadLocalRandom.current().nextInt(150, Integer.MAX_VALUE) + "");
    this.me = new UserDTO(true, tourist, "password");

    HttpEntity<UserDTO> req = new HttpEntity<>(me);

    ResponseEntity<TokenResponse> res = restTemplate.postForEntity(HOST + "/register", req, TokenResponse.class);
    TokenResponse token = res.getBody();

    jdbcTemplate.update("UPDATE Users SET admin = 1 FROM Users INNER JOIN Tourists ON " +
        "Users.tourist = Tourists.touristId WHERE Tourists.idCard = ? ", token.getCardId());

    this.headers.setBearerAuth(token.getToken());
  }

  @Test
  @SuppressWarnings("null")
  void shouldGetAPageWithAtLeastTwoCities() {
    var res = restTemplate.exchange(this.HOST + "/city", HttpMethod.GET, null,
        new ParameterizedTypeReference<PageResponse<CityDTO>>() {
        });

    assertEquals(HttpStatus.OK, res.getStatusCode());
    assertNotNull(res.getBody());
    assertTrue(res.getBody().getLenght() >= 2);
  }

  @Test
  @SuppressWarnings("null")
  void shouldAddAndDeleteACityIfCredentialsArePassed() {
    var toSave = new CityDTO();

    toSave.setMostReserverdHotel("new Reservated Hotel");
    toSave.setMostTuristicPlace("new touristic place");
    toSave.setName("new city");
    toSave.setPopulation(10001);

    var resSave = restTemplate.postForEntity(this.HOST + "/city", new HttpEntity<CityDTO>(toSave, this.headers),
        CityDTO.class);

    assertEquals(HttpStatus.OK, resSave.getStatusCode());
    assertNotNull(resSave.getBody());
    assertNotNull(resSave.getBody().getId());
    assertEquals(resSave.getBody().getName(), toSave.getName());
    assertEquals(resSave.getBody().getPopulation(), toSave.getPopulation());
    assertEquals(resSave.getBody().getMostReserverdHotel(), toSave.getMostReserverdHotel());
    assertEquals(resSave.getBody().getMostTuristicPlace(), toSave.getMostTuristicPlace());

    // Verify directly in database
    var find = findByID(resSave.getBody().getId()).get(0);

    assertEquals(find.get("cityId"), resSave.getBody().getId());
    assertEquals(find.get("name"), resSave.getBody().getName());
    assertEquals(find.get("mostTuristicPlace"), resSave.getBody().getMostTuristicPlace());
    assertEquals(find.get("mostReserverdHotel"), resSave.getBody().getMostReserverdHotel());
    assertEquals(find.get("population"), resSave.getBody().getPopulation());

    // Deletion

    var deleteRes = restTemplate.exchange(this.HOST + "/city", HttpMethod.DELETE,
        new HttpEntity<>(resSave.getBody(), this.headers), Void.class);

    assertEquals(HttpStatus.OK, deleteRes.getStatusCode());

    var findInDB = findByID(resSave.getBody().getId());

    assertEquals(0, findInDB.size());
  }

  @Test
  @SuppressWarnings("null")
  void shouldUpdateACity() {
    var updated = new CityDTO();
    updated.setId(126);
    updated.setName("Frist Updated");
    updated.setPopulation(1224);

    var body = new HttpEntity<CityDTO>(updated, this.headers);

    var resUpdate = restTemplate.exchange(this.HOST + "/city", HttpMethod.PUT, body, CityDTO.class);
    var fromDb = findByID(updated.getId());

    assertEquals(HttpStatus.OK, resUpdate.getStatusCode());
    assertNotNull(resUpdate.getBody());
    assertEquals(fromDb.get(0).get("name"), resUpdate.getBody().getName());
    assertEquals(fromDb.get(0).get("mostReserverdHotel"), resUpdate.getBody().getMostReserverdHotel());
    assertEquals(fromDb.get(0).get("mostTuristicPlace"), resUpdate.getBody().getMostTuristicPlace());
    assertEquals(fromDb.get(0).get("population"), resUpdate.getBody().getPopulation());
  }

  List<Map<String, Object>> findByID(Integer id) {
    return jdbcTemplate.queryForList("SELECT * FROM Cities WHERE cityId = ?", id);
  }

  @AfterAll
  void delete() {
    var req = new HttpEntity<>(this.headers);
    restTemplate.exchange(HOST + "/tourist", HttpMethod.DELETE, req, Void.class);
  }
}
