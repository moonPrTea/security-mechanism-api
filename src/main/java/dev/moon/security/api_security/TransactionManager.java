package dev.moon.security.api_security;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;


@Component
public class TransactionManager {

  private final SessionFactory sessionFactory;

  public TransactionManager(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public <T> T inTransaction(Supplier<T> supplier) {
    Optional<Transaction> tx = beginTransaction();
    try {
      T result = supplier.get();
      tx.ifPresent(Transaction::commit);
      return result;
    } catch (RuntimeException e) {
      tx.filter(Transaction::isActive).ifPresent(Transaction::rollback);
      throw e;
    }
  }

  public void inTransaction(Runnable runnable) {
    inTransaction(() -> {
      runnable.run();
      return null;
    });
  }

  private Optional<Transaction> beginTransaction() {
    Transaction transaction = sessionFactory.getCurrentSession().getTransaction();
    if (transaction.isActive()) {
      return Optional.empty();
    }
    transaction.begin();
    return Optional.of(transaction);
  }
}
