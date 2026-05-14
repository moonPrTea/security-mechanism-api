package dev.moon.security.api_security.service;

import dev.moon.security.api_security.dao.IdempotencyDao;
import dev.moon.security.api_security.manager.TransactionManager;
import dev.moon.security.api_security.model.IdempotencyKeys;
import dev.moon.security.api_security.model.PaymentRequestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IdempotencyService {

  private static final Logger LOGGER = LoggerFactory.getLogger(IdempotencyService.class);

  @Autowired
  IdempotencyDao idempotencyDao;

  @Autowired
  TransactionManager transactionManager;

  public IdempotencyKeys checkIdempotencyKeyExists(String idempotencyKey) {
    return transactionManager.inTransaction(() -> idempotencyDao.checkIdempotencyKeyExists(idempotencyKey));
  }

  public PaymentRequestStatus checkPaymentExists(IdempotencyKeys idempotencyRecord, String paymentDtoHash) {
    if (!idempotencyRecord.getRequestHash().equals(paymentDtoHash)) {
      LOGGER.info("Idempotency key reuse with different payload");
      return PaymentRequestStatus.INVALID_KEY;
    }

    if (idempotencyRecord.getResponseBody() != null) {
      LOGGER.info("Replay, response with current idempotency key exists");
      return PaymentRequestStatus.ACCEPTED;
    }

    LOGGER.info("Same idempotency key and payload");
    return PaymentRequestStatus.PROCESSING;
  }

  public IdempotencyKeys savePaymentOperation(String idempotencyKey, String requestHash) {
    IdempotencyKeys idempotencyKeys = new IdempotencyKeys(idempotencyKey, requestHash);
    idempotencyDao.savePaymentOperation(idempotencyKeys);

    return idempotencyKeys;
  }

}
