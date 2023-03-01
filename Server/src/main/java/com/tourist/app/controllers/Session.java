package com.tourist.app.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

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

import com.tourist.app.database.users.User;
import com.tourist.app.entity.TokenResponse;
import com.tourist.app.entity.dto.UserDTO;
import com.tourist.app.services.database.ITouristService;
import com.tourist.app.services.database.IUserService;
import com.tourist.app.utils.TokenGenerator;

/**
 * It's a REST controller that handles regitration
 */
@RestController
@RequestMapping
public class Session {
  private static final Logger LOGGER = Logger.getLogger("com.tourist.app.controllers.Session");

  @Autowired
  private IUserService userService;
  @Autowired
  private ITouristService touristService;

  /**
   * It receives a userDTO, checks if the user is valid, checks if the tourist is
   * valid, checks if the
   * tourist exists, if not, it creates it, then it creates the user and returns a
   * token
   * 
   * @param user The user to be registered.
   * @return A tokenResponse object
   */
  @PostMapping("/register")
  public ResponseEntity<TokenResponse> register(@RequestBody UserDTO user) {
    if (user == null ||
        user.getId() != null ||
        user.getPassword() == null ||
        user.getTourist() == null ||
        user.getTourist().getIdCard() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operation failed with status code 400");
    }
    
    var userEntity = UserDTO.dtoToUser(user);
    ResponseEntity<TokenResponse> res;
    userEntity.setAdmin(false);
    userEntity.getTourist().setId(null);
    userEntity.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

    final var find = touristService.getByIdCard(user.getTourist().getIdCard());

    if (find.isEmpty()) {
      saveTourist(userEntity);
    } else {
      userEntity.setTourist(find.get());
    }

    try {
      final var saved = userService.save(userEntity);

      final var token = TokenGenerator.createToken(saved.getTourist().getIdCard());
      final var tokenResponse = new TokenResponse(token, userEntity.getTourist().getIdCard(), "Bearer ",
          saved.getAdmin());

      res = ResponseEntity.ok().body(tokenResponse);

    } catch (DataIntegrityViolationException e) {
      LOGGER.log(Level.INFO, e.getMessage(), e);
      throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "That tourist has an associated user");
    }

    return res;
  }
  
  void saveTourist(User user) {
    if (user.getTourist().getBornDate() == null ||
        user.getTourist().getName() == null ||
        user.getTourist().getLastName() == null ||
        user.getTourist().getTravelBudget() == null ||
        user.getTourist().getTravelFrequency() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operation failed with status 400");
    }
    try {
      var newTourist = touristService.save(user.getTourist());
      user.setTourist(newTourist);
    } catch (DataIntegrityViolationException e) {
      LOGGER.log(Level.INFO, e.getMessage(), e);
      throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "There is an tourist with that cardId ");
    }
  }
}
