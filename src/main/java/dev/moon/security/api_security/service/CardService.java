package dev.moon.security.api_security.service;

import dev.moon.security.api_security.manager.TransactionManager;
import dev.moon.security.api_security.dao.CardDao;
import dev.moon.security.api_security.dao.UserDao;
import dev.moon.security.api_security.dto.CardRecord;
import dev.moon.security.api_security.model.UserCard;
import dev.moon.security.api_security.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CardService {

  @Autowired
  CardDao cardDao;

  @Autowired
  UserAuthService userAuthService;

  @Autowired
  TransactionManager transactionManager;

  @Autowired
  UserDao userDao;

  public CardRecord createCard() {
    UUID userId = userAuthService.getCurrentUserId();

    UserCard card = transactionManager.inTransaction(() -> {
      Users user = userDao.getUser(userId);

      UserCard userCard = new UserCard(BigDecimal.ZERO, user);
      cardDao.createCard(userCard);

      return userCard;
    });

    return new CardRecord(card.getId(), card.getBalance(),
            card.getCreatedAt());
  }

  public UserCard checkCardExists(UUID cardId) {
    return cardDao.checkCardExists(cardId);
  }

  public void changeCardBalance(UserCard userCard) {
    cardDao.changeCardBalance(userCard);
  }
}
