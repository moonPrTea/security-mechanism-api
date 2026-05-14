package dev.moon.security.api_security.dao;

import dev.moon.security.api_security.model.IdempotencyKeys;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IdempotencyDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(IdempotencyDao.class);

  @Autowired
  SessionFactory sessionFactory;

  public IdempotencyKeys checkIdempotencyKeyExists(String key) {
    return sessionFactory.getCurrentSession()
            .createQuery(
                    "from IdempotencyKeys where idempotencyKey = :key",
                    IdempotencyKeys.class)
            .setParameter("key", key)
            .uniqueResult();
  }

  public void savePaymentOperation(IdempotencyKeys idempotencyKeys) {
    this.sessionFactory.getCurrentSession()
            .persist(idempotencyKeys);

    LOGGER.info("Payment operation with idempotency key: %s performed successfully", idempotencyKeys.getIdempotencyKey());
  }
}
