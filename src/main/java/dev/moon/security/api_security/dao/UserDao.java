package dev.moon.security.api_security.dao;

import dev.moon.security.api_security.model.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public class UserDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

  @Autowired
  SessionFactory sessionFactory;

  public void createUser(Users user) {
    this.sessionFactory.getCurrentSession()
            .persist(user);

    LOGGER.info("User with surname: %s created successfully", user.getFirstName());
  }

  public Users getUser(UUID id) {
    return (Users) this.sessionFactory.getCurrentSession()
            .find(Users.class, id);
  }

  public Users getUserByUsername(String username) {
    try (Session session = this.sessionFactory.openSession()) {
      return session
              .createQuery("from Users u where u.username = :username", Users.class)
              .setParameter("username", username)
              .uniqueResult();
    }
  }
}
