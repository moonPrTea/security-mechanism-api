package dev.moon.security.api_security.service;

import dev.moon.security.api_security.dao.CurrencyDao;
import dev.moon.security.api_security.manager.TransactionManager;
import dev.moon.security.api_security.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CurrencyService {
  @Autowired
  CurrencyDao currencyDao;

  @Autowired
  TransactionManager transactionManager;

  public Currency checkCurrencyExists(String currencyCode) {
    return transactionManager.inTransaction(() -> currencyDao.checkCurrencyExist(currencyCode));
  }

  public String createCurrency(String code) {
    Currency currency = new Currency(code);
    transactionManager.inTransaction(() -> currencyDao.createCurrency(currency));

    return currency.getCode();
  }
}
