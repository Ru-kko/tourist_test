package com.tourist.app.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

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
import org.springframework.web.client.HttpStatusCodeException;

import com.tourist.app.database.tourists.Tourist;
import com.tourist.app.entity.PageResponse;
import com.tourist.app.entity.TokenAndTourist;
import com.tourist.app.entity.TokenResponse;
import com.tourist.app.entity.dto.TouristDTO;
import com.tourist.app.entity.dto.UserDTO;

import lombok.var;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TouristApiTest {
  @Autowired
  TestRestTemplate restTemplate;
  @LocalServerPort
  Integer port;
  @Autowired
  JdbcTemplate jdbcTemplate;

  UserDTO me;
  String HOST;
  HttpHeaders headers;

  @BeforeAll
  @SuppressWarnings("null")
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

  @Test
  void shouldReturnAnErrorWhenTryToDeleteAUserWithoutToken() {
    try {
      restTemplate.delete(HOST + "/tourist", Void.class);
    } catch (HttpStatusCodeException e) {
      assertEquals(403, e.getStatusCode().value());
    }
  }

  @Test
  void shouldReturAnErrorWhenTryToUpdateWithoutToken() {
    var clon = this.me.getTourist();

    clon.setLastName("foo");
    var req = new HttpEntity<TouristDTO>(clon);

    try {
      restTemplate.put(HOST + "/tourist", req, TouristDTO.class);
    } catch (HttpStatusCodeException e) {
      assertEquals(403, e.getStatusCode().value());
    }
  }

  @Test
  @SuppressWarnings("null")
  void shouldMakeChangesOnRelatedTourist() {
    var allRes = restTemplate.exchange(HOST + "/tourst", HttpMethod.GET, null,
        new ParameterizedTypeReference<PageResponse<TouristDTO>>() {});

    assertNotNull(allRes.getBody());
    assertTrue(allRes.getBody().getLenght() >= 1);

    var meRes = restTemplate.exchange(HOST + "/tourist/@me", HttpMethod.GET, new HttpEntity<>(this.headers),
        TouristDTO.class);

    assertNotNull(meRes);
    assertEquals(meRes.getBody().getName(), me.getTourist().getName());
    assertEquals(meRes.getBody().getLastName(), me.getTourist().getLastName());
    assertEquals(meRes.getBody().getIdCard(), me.getTourist().getIdCard());
    assertEquals(meRes.getBody().getBornDate(), me.getTourist().getBornDate());
    assertEquals(meRes.getBody().getTravelBudget(), me.getTourist().getTravelBudget());
    assertEquals(meRes.getBody().getTravelFrequency(), me.getTourist().getTravelFrequency());

    var clon = this.me.getTourist();
    clon.setName("bar");
    clon.setLastName("foo");

    ResponseEntity<TokenAndTourist> res1 = restTemplate.exchange(HOST + "/tourist", HttpMethod.PUT,
        new HttpEntity<>(clon, this.headers),
        TokenAndTourist.class);
    var tAfterChange = jdbcTemplate.queryForMap("SELECT * FROM Tourists WHERE idCard = ? ", clon.getIdCard());

    assertNotNull(res1.getBody());
    assertEquals(HttpStatus.OK, res1.getStatusCode());
    assertEquals("foo", res1.getBody().getTourist().getLastName());
    assertEquals("bar", res1.getBody().getTourist().getName());
    assertEquals(clon.getBornDate(), res1.getBody().getTourist().getBornDate());
    assertEquals(clon.getIdCard(), res1.getBody().getTourist().getIdCard());
    assertEquals(clon.getTravelBudget(), res1.getBody().getTourist().getTravelBudget());
    assertEquals(clon.getTravelFrequency(), res1.getBody().getTourist().getTravelFrequency());

    assertEquals(tAfterChange.get("idCard"), clon.getIdCard());
    assertEquals(tAfterChange.get("name"), clon.getName());
    assertEquals(tAfterChange.get("lastName"), clon.getLastName());
    assertEquals(tAfterChange.get("travelBudget"), clon.getTravelBudget());

    // Sould delete
    var req2 = new HttpEntity<>(this.headers);
    var res2 = restTemplate.exchange(HOST + "/tourist", HttpMethod.DELETE, req2, Void.class);

    assertEquals(HttpStatus.OK.value(), res2.getStatusCode().value());

    var resAfterDelete = jdbcTemplate.queryForList("SELECT * FROM Tourists WHERE idCard = ? ", Tourist.class,
        clon.getIdCard());

    assertEquals(0, resAfterDelete.size());

    // retry should be an error
    try {
      restTemplate.exchange(HOST + "/tourist", HttpMethod.DELETE, req2, Void.class);
    } catch (HttpStatusCodeException e) {
      assertEquals(403, e.getStatusCode().value());
    }
  }
}
