package com.tourist.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tourist.app.dataBase.users.User;
import com.tourist.app.entity.TokenResponse;
import com.tourist.app.services.db.Tourists;
import com.tourist.app.services.db.Users;
import com.tourist.app.utils.TokenGenerator;

@RestController
@RequestMapping
public class Session {

  @Autowired
  private Users userService;
  @Autowired
  private Tourists touristService;

  @PostMapping("/register")
  public ResponseEntity<TokenResponse> register(@RequestBody User user) {
    ResponseEntity<TokenResponse> res;

    if (user == null ||
        user.getId() != null ||
        user.getPassword() == null ||
        user.getTourist() == null ||
        user.getTourist().getIdCard() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operation failed with status code 400");

    }
    user.setAdmin(false);
    user.getTourist().setId(null);
    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

    final var find = touristService.getByIdCard(user.getTourist().getIdCard());

    if (find.isEmpty()) {
      if (user.getTourist().getBornDate() == null ||
          user.getTourist().getFullName() == null ||
          user.getTourist().getTravelBudget() == null ||
          user.getTourist().getTravelFrequency() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operation failed with status 400");
      }
      try {
        var newTourist = touristService.save(user.getTourist());
        user.setTourist(newTourist);
      } catch (DataIntegrityViolationException e) {
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "There is an tourist with that cardId ");
      }
    } else {
      user.setTourist(find.get());
    }

    try {
      final var saved = userService.save(user);

      final var token = TokenGenerator.createToken(saved.getTourist().getIdCard());
      final var tokenResponse = new TokenResponse(token, user.getTourist().getIdCard(), "Bearer", user.getAdmin());

      res = ResponseEntity.ok().body(tokenResponse);

    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "That tourist has an associated user");
    }

    return res;
  }
}
