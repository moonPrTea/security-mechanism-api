package dev.moon.security.api_security.dao;

import dev.moon.security.api_security.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class UserDao {
  ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<>();

  public User createUser(User user) {
    UUID id = UUID.randomUUID();

    User userRecord = new User(id, user.firstName(), user.secondName(), user.middleName(), user.createdAt());

    users.put(id, userRecord);
    return userRecord;
  }

  public List<User> getUsers() {
    return users.values().stream().toList();
  }

  public User getUser(UUID id) {
    return users.values().stream()
            .filter(user -> Objects.equals(user.id(), id))
            .findFirst()
            .orElse(null);
  }
}
