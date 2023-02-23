package com.tourist.app.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import com.tourist.app.entity.TokenAndTourist;
import com.tourist.app.entity.TokenResponse;
import com.tourist.app.entity.dto.TouristDTO;
import com.tourist.app.entity.dto.UserDTO;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TouristApiTest {
  @Autowired
  TestRestTemplate restTemplate;
  @LocalServerPort
  Integer port;
  TokenResponse token;
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
    this.token = res.getBody();
    
    this.headers.setBearerAuth(this.token.getToken());
  }

  @Test
  void should_return_an_error_when_try_to_delete_user_without_token() {
    try {
      restTemplate.delete(HOST + "/tourist", Void.class);
    } catch (HttpStatusCodeException e) {
      assertEquals(403, e.getStatusCode().value());
    }
  }

  @Test
  void should_retur_an_error_when_try_to_update_without_token() {
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
  void should_make_changes_on_related_tourist() {
    var clon = this.me.getTourist();
    clon.setName("bar");
    clon.setLastName("foo");

    ResponseEntity<TokenAndTourist> res1 = restTemplate.exchange(HOST + "/tourist", HttpMethod.PUT,
        new HttpEntity<>(clon, this.headers),
        TokenAndTourist.class);

    assertNotNull(res1.getBody());
    assertEquals(HttpStatus.OK, res1.getStatusCode());
    assertEquals("foo", res1.getBody().getTourist().getLastName());
    assertEquals("bar", res1.getBody().getTourist().getName());


    // Sould delete 
    var req2 = new HttpEntity<>(this.headers);
    var res2 = restTemplate.exchange(HOST + "/tourist", HttpMethod.DELETE, req2, Void.class);
  
    assertEquals(HttpStatus.OK.value(), res2.getStatusCode().value());
  
    // retry should be an error
    try {
      restTemplate.exchange(HOST + "/tourist", HttpMethod.DELETE, req2, Void.class);
    } catch (HttpStatusCodeException e) {
      assertEquals(403, e.getStatusCode().value());
    }
  }


}
