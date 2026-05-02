package dev.moon.security.api_security.service;


import dev.moon.security.api_security.TransactionManager;
import dev.moon.security.api_security.dao.UserDao;
import dev.moon.security.api_security.model.Users;
import dev.moon.security.api_security.dto.CreateUserDto;
import jakarta.transaction.Transaction;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
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

  public Users createUser(CreateUserDto userDto) {
    Users user = new Users(userDto.firstName(), userDto.secondName(), userDto.middleName());
    transactionManager
            .inTransaction(() -> userDao.createUser(user));

    return user;
  }

  public Users getUser(UUID id) {
    return transactionManager
            .inTransaction(() -> userDao.getUser(id));
  }

}
