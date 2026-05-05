package dev.moon.security.api_security.service;

import dev.moon.security.api_security.dao.UserDao;
import dev.moon.security.api_security.security.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class UserAuthService {

  @Autowired
  UserDao userDao;

  public UUID getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new ResponseStatusException(UNAUTHORIZED, "User isn't authenticated");
    }

    Object principalAuth = authentication.getPrincipal();
    if (principalAuth instanceof AuthUser authUser) {
      return authUser.getUserId();
    }

    throw new ResponseStatusException(UNAUTHORIZED, "Not valid authorization principal");
  }
}
