package dev.moon.security.api_security.dao;

import dev.moon.security.api_security.model.UserCard;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CardDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

  @Autowired
  SessionFactory sessionFactory;

  public void createCard(UserCard card) {
    this.sessionFactory.getCurrentSession()
            .persist(card);

    LOGGER.info("Card with id: %s created successfully", card.getId());
  }

  public UserCard checkCardExists(UUID cardId) {
    return this.sessionFactory.getCurrentSession()
            .find(UserCard.class, cardId);
  }

  public void changeCardBalance(UserCard userCard) {
    this.sessionFactory.getCurrentSession()
                    .merge(userCard);

    LOGGER.info("Updated card balance successfully");
  }
}
