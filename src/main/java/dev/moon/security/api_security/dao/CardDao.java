package dev.moon.security.api_security.dao;

import dev.moon.security.api_security.model.UserCard;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CardDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

  @Autowired
  SessionFactory sessionFactory;

  public void createCard(UserCard card) {
    this.sessionFactory.getCurrentSession()
            .persist(card);

    LOGGER.info("User with id: %s created successfully", card.getId());
  }
}
