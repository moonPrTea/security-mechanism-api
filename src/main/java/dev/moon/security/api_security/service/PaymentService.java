package dev.moon.security.api_security.service;

import dev.moon.security.api_security.dao.CurrencyDao;
import dev.moon.security.api_security.dao.PaymentDao;
import dev.moon.security.api_security.dto.CreatePayment;
import dev.moon.security.api_security.dto.OperationTypeDto;
import dev.moon.security.api_security.manager.TransactionManager;
import dev.moon.security.api_security.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentService {
  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

  @Autowired
  IdempotencyService idempotencyService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  CardService cardService;

  @Autowired
  TransactionManager transactionManager;

  @Autowired
  UserAuthService userAuthService;

  @Autowired
  PaymentDao paymentDao;

  @Autowired
  CurrencyService currencyService;

  public PaymentRequestStatus createPaymentOperation(String idempotencyKey, String paymentDtoHash,
                            CreatePayment createPayment) {
    UUID userId = userAuthService.getCurrentUserId();

    IdempotencyKeys idempotencyRecord = idempotencyService.checkIdempotencyKeyExists(idempotencyKey);
    if (idempotencyRecord != null) {
      return idempotencyService
              .checkPaymentExists(idempotencyRecord, paymentDtoHash);
    }

    return transactionManager.inTransaction(() -> {
      IdempotencyKeys newIdempotencyOperation = idempotencyService
              .savePaymentOperation(idempotencyKey, paymentDtoHash);

      UserCard userCardRecord = cardService.checkCardExists(createPayment.cardId());
      UserCard destinationCardRecord = cardService.checkCardExists(createPayment.destinationCardId());
      BigDecimal paymentAmount = createPayment.amount();
      Currency currency = currencyService.checkCurrencyExists(createPayment.currency());

      PaymentRequestStatus validPaymentRequestStatus = checkRequestValues(userCardRecord,
              destinationCardRecord, userId, paymentAmount,
              createPayment.currency());
      if (validPaymentRequestStatus != null) {
        return validPaymentRequestStatus;
      }

      if (currency == null) {
        LOGGER.info("Currency isn't presented in db records");
        return PaymentRequestStatus.INVALID_CURRENCY;
      }

      String operation = "PAYMENT";
      OperationType operationType = checkOperationTypeExists(operation);
      if (operationType == null) {
       operationType = addOperationTypeValues(operation);
      }

      userCardRecord.setBalance(userCardRecord.getBalance().subtract(paymentAmount));
      cardService.changeCardBalance(userCardRecord);

      destinationCardRecord.setBalance(destinationCardRecord.getBalance().add(paymentAmount));
      cardService.changeCardBalance(destinationCardRecord);

      newIdempotencyOperation.setResponseBody(
              objectMapper.writeValueAsString(Map.of("status", PaymentRequestStatus.PROCESSED)));
      savePaymentFinancialOperation(userCardRecord, destinationCardRecord, createPayment, operationType, currency);

      return PaymentRequestStatus.PROCESSED;
    });
  }

  public void savePaymentFinancialOperation(UserCard senderCard, UserCard destinationCard, CreatePayment createPayment,
                                            OperationType operationType, Currency currency) {
    FinancialOperation financialOperation = new FinancialOperation(senderCard, destinationCard, operationType, currency, createPayment.amount());

    paymentDao.createPaymentRecord(financialOperation);
  }

  public OperationType addOperationTypeValues(String operation) {
    OperationType operationType = new OperationType(operation);
    paymentDao.addOperationTypeValue(operationType);

    return operationType;
  }

  public OperationType checkOperationTypeExists(String operationType) {
    return paymentDao.checkOperationTypeExists(operationType);
  }

  private PaymentRequestStatus checkRequestValues(UserCard userCardRecord, UserCard destinationCardRecord,
                                                  UUID userId, BigDecimal paymentAmount, String currency) {
    if (userCardRecord == null || destinationCardRecord == null ) {
      LOGGER.info("No cards were found");
      return PaymentRequestStatus.BLOCKED;
    }

    if (!userCardRecord.getUser().getId().equals(userId)) {
      LOGGER.info("Authorized user isn't current card owner");
      return PaymentRequestStatus.BLOCKED;
    }

    if (userCardRecord.getBalance().compareTo(paymentAmount) < 0) {
      return PaymentRequestStatus.CONFLICT;
    }

    return null;
  }
}
