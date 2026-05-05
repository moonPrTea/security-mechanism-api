package dev.moon.security.api_security.service;


import dev.moon.security.api_security.TransactionManager;
import dev.moon.security.api_security.dao.UserDao;
import dev.moon.security.api_security.dto.UserRecord;
import dev.moon.security.api_security.model.Users;
import dev.moon.security.api_security.dto.CreateUserDto;
import jakarta.transaction.Transaction;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

  @Autowired
  UserDao userDao;

  @Autowired
  TransactionManager transactionManager;

  @Autowired
  PasswordEncoder passwordEncoder;

  public UserRecord createUser(CreateUserDto userDto) {
    String passwordHash = passwordEncoder.encode(userDto.password());

    Users user = new Users(userDto.firstName(), userDto.secondName(), userDto.middleName(), userDto.username(), passwordHash);
    transactionManager
            .inTransaction(() -> userDao.createUser(user));

    return new UserRecord(user.getId(), user.getFirstName(), user.getSecondName(), user.getMiddleName(), user.getUsername(), user.getCreatedAt());
  }

  public UserRecord getUser(UUID id) {
    Users user =  transactionManager
            .inTransaction(() -> userDao.getUser(id));

    return new UserRecord(user.getId(), user.getFirstName(), user.getSecondName(), user.getMiddleName(), user.getUsername(), user.getCreatedAt());
  }

}
