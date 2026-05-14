package dev.moon.security.api_security.dao;

import dev.moon.security.api_security.model.FinancialOperation;
import dev.moon.security.api_security.model.OperationType;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentDao.class);

  @Autowired
  SessionFactory sessionFactory;

  public void addOperationTypeValue(OperationType operationType) {
    this.sessionFactory.getCurrentSession()
            .persist(operationType);

    LOGGER.info("Operation type with id: %s was created successfully", operationType.getId());
  }

  public void createPaymentRecord(FinancialOperation financialOperation) {
    this.sessionFactory.getCurrentSession()
            .persist(financialOperation);

    LOGGER.info("Financial operation record with id: %s was created successfully", financialOperation.getId());
  }

  public OperationType checkOperationTypeExists(String operationType) {
    return this.sessionFactory.getCurrentSession()
            .createQuery("from OperationType where operation = :operation", OperationType.class)
            .setParameter("operation", operationType)
            .uniqueResult();
  }
}
