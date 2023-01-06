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

import com.tourist.app.dataBase.users.User;
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
  public ResponseEntity<String> register(@RequestBody User user) {
    ResponseEntity<String> res;

    if (user == null ||
        user.getId() != null ||
        user.getPassword() == null ||
        user.getTourist() == null ||
        user.getTourist().getIdCard() == null) {
      res = new ResponseEntity<>("Operation failed with status code 400", HttpStatus.BAD_REQUEST);
      return res;
    }
    user.setAdmin(false);
    user.getTourist().setId(null);
    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

    var find = touristService.getByIdCard(user.getTourist().getIdCard());

    if (find.isEmpty()) {
      if (user.getTourist().getBornDate() == null ||
          user.getTourist().getFullName() == null ||
          user.getTourist().getTravelBudget() == null ||
          user.getTourist().getTravelFrequency() == null) {
        res = new ResponseEntity<>("Operation failed with status 400", HttpStatus.BAD_REQUEST);
        return res;
      }
      try {
        var newTourist = touristService.save(user.getTourist());
        user.setTourist(newTourist);
      } catch (DataIntegrityViolationException e) {
        res = new ResponseEntity<>("There is an tourist with that cardId ", HttpStatus.NOT_ACCEPTABLE);
        return res;
      }
    } else {
      user.setTourist(find.get());
    }
    
    try {
      var saved = userService.save(user);

      var token = TokenGenerator.createToken(saved.getTourist().getIdCard());
      res = ResponseEntity.ok().header("Authorization", "Bearer " + token).body(null);

    } catch (DataIntegrityViolationException e) {
      res = new ResponseEntity<>("That tourist has an associated user", HttpStatus.NOT_ACCEPTABLE);
    }
    
    return res;
  }
}
