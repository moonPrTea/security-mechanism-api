package dev.moon.security.api_security.service;


import dev.moon.security.api_security.dao.UserDao;
import dev.moon.security.api_security.model.User;
import dto.CreateUserDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {
  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public List<User> getUsers() {
    return userDao.getUsers();
  }

  public User getUserWithId(int userId) {
    return userDao.getUser(userId);
  }

  public User createUser(CreateUserDto userDto) {
    User user = new User(null, userDto.firstName(), userDto.secondName(),
            userDto.middleName(), Instant.now());

    return userDao.createUser(user);
  }
}
