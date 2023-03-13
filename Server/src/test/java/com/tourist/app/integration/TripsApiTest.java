package com.tourist.app.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.sql.Date;

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
import com.tourist.app.entity.dto.TouristDTO;
import com.tourist.app.entity.dto.TripDTO;
import com.tourist.app.entity.dto.UserDTO;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/dataDrop.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/dataInit.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@SuppressWarnings("null")
class TripsApiTest {
  @Autowired
  TestRestTemplate restTemplate;
  @Autowired
  JdbcTemplate jdbcTemplate;
  @LocalServerPort
  Integer port;

  UserDTO me;
  String HOST;
  HttpHeaders headers;

  @BeforeAll
  void initialize() {
    this.HOST = "http://localhost:" + port;
    this.headers = new HttpHeaders();

    // Registration
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

    this.headers.setBearerAuth(token.getToken());
  }

  @AfterAll
  void delete() {
    var req = new HttpEntity<>(this.headers);
    restTemplate.exchange(HOST + "/tourist", HttpMethod.DELETE, req, Void.class);
  }

  @Test
  void couldntSaveMoreThan5TripsInTheSameDay() {

    var res1 = restTemplate.postForEntity(HOST + "/city/124?day=2023-10-04", new HttpEntity<>(headers), TripDTO.class);

    assertEquals(HttpStatus.OK, res1.getStatusCode());
    assertNotNull(res1.getBody());
    assertEquals(LocalDate.of(2023,10,4), res1.getBody().getStartDate());
    
    // Find in db
    var resForDb = jdbcTemplate.queryForMap("SELECT * FROM Trips WHERE tripId = ?", res1.getBody().getId());

    assertEquals(resForDb.get("tripId"), res1.getBody().getId());
    assertEquals(resForDb.get("cityId"), res1.getBody().getCity().getId());
    assertEquals(resForDb.get("touristId"), res1.getBody().getTourist().getId());
    var date = ((Date)resForDb.get("startDate")).toLocalDate();
    assertEquals(date , res1.getBody().getStartDate());


    // should dont save more at this day
    var res2 = restTemplate.postForEntity(HOST + "/city/124?day=2023-10-04", new HttpEntity<>(headers), TripDTO.class);
    assertEquals(HttpStatus.BAD_REQUEST, res2.getStatusCode());

    // should delete
    var res3 = restTemplate.exchange(HOST + "/city/trip/" + res1.getBody().getId(), HttpMethod.DELETE, new HttpEntity<>(this.headers), Void.class);
    
    assertEquals(HttpStatus.OK, res3.getStatusCode());
  }

  @Test
  void shouldReturnsAPageOfTripsOfAnUser() {
    var res = restTemplate.exchange(HOST + "/tourist/125", HttpMethod.GET, null ,new ParameterizedTypeReference<PageResponse<TripDTO>>() {});

    assertEquals(HttpStatus.OK, res.getStatusCode());
    assertNotNull(res.getBody());
    assertEquals(2, res.getBody().getLenght());
  }


  @Test
  void shouldReturnsAPageOfTripsOfACityWithAtLeastFourTrips() {
    var res = restTemplate.exchange(HOST + "/city/124", HttpMethod.GET, null ,new ParameterizedTypeReference<PageResponse<TripDTO>>() {});

    assertEquals(HttpStatus.OK, res.getStatusCode());
    assertNotNull(res.getBody());
    assertTrue(4 <= res.getBody().getLenght());
  }
}
