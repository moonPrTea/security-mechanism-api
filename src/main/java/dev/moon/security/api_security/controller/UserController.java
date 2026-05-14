package dev.moon.security.api_security.controller;

import dev.moon.security.api_security.dto.BaseResponse;
import dev.moon.security.api_security.dto.UserRecord;
import dev.moon.security.api_security.service.UserService;
import dev.moon.security.api_security.dto.CreateUserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  UserService userService;

  @GetMapping("/status")
  public ResponseEntity<?> checkStatus() {
    return ResponseEntity.status(HttpStatus.OK)
            .body(new BaseResponse("success", "Route is working"));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserWithId(@PathVariable("id") UUID userId) {
    UserRecord user = userService.getUser(userId);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new BaseResponse("error", "Didn't find user with current id"));
    }

    return ResponseEntity.ok(user);
  }

  @PostMapping
  public UserRecord createUser(@RequestBody @Valid CreateUserDto userDto) {
    return userService.createUser(userDto);
  }
}
