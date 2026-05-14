package dev.moon.security.api_security.dao;

import  dev.moon.security.api_security.model.Currency;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public class CurrencyDao {
  private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyDao.class);

  @Autowired
  SessionFactory sessionFactory;

  public Currency checkCurrencyExist(String currencyCode) {
    return this.sessionFactory.getCurrentSession()
            .createQuery("from Currency where code = :code", Currency.class)
            .setParameter("code", currencyCode)
            .uniqueResult();
  }

  public Currency getCurrencyById(UUID currencyId) {
    return this.sessionFactory.getCurrentSession()
            .find(Currency.class, currencyId);
  }

  public void createCurrency(Currency currency) {
    this.sessionFactory.getCurrentSession()
            .persist(currency);
  }
}
