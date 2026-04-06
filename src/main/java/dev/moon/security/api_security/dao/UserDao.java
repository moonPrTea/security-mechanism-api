package dev.moon.security.api_security.dao;

import dev.moon.security.api_security.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Repository
public class UserDao {
  HashMap<Integer, User> users = new HashMap<>();
  private final AtomicInteger lastId = new AtomicInteger(0);

  public User createUser(User user) {
    Integer id = lastId.incrementAndGet();
    User userRecord = new User(id, user.firstName(), user.secondName(), user.middleName(), user.createdAt());

    users.put(id, userRecord);
    return userRecord;
  }

  public List<User> getUsers() {
    return users.values().stream().toList();
  }

  public User getUser(Integer id) {
    return users.values().stream()
            .filter(user -> Objects.equals(user.id(), id))
            .findFirst()
            .orElse(null);
  }
}
