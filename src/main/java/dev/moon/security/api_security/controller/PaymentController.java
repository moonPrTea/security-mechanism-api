package dev.moon.security.api_security.controller;

import dev.moon.security.api_security.dto.CreatePayment;
import dev.moon.security.api_security.dto.OperationTypeDto;
import dev.moon.security.api_security.manager.HashManager;
import dev.moon.security.api_security.model.PaymentRequestStatus;
import dev.moon.security.api_security.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;


import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  HashManager hashManager;

  @Autowired
  PaymentService paymentService;


  @PostMapping
  public ResponseEntity<?> createPayment(@RequestHeader("Idempotency-Key") String key,
                                         @RequestBody @Valid CreatePayment createPayment) throws NoSuchAlgorithmException {
    String paymentDtoHash = hashManager.getDtoHash(createPayment);

    PaymentRequestStatus requestStatus = paymentService.createPaymentOperation(key, paymentDtoHash, createPayment);

    // add switch
    return ResponseEntity
            .ok().body(Map.of("status", requestStatus.getStatusDescription()));
  }

  @PostMapping("/operation_type")
  public ResponseEntity<?> createOperationType(@RequestBody @Valid OperationTypeDto operationTypeDto) {
    return ResponseEntity
            .ok()
            .body(Map.of("operationType", paymentService.addOperationTypeValues(operationTypeDto.operation())));
  }
}
