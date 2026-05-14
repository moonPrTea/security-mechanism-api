package dev.moon.security.api_security.controller;

import dev.moon.security.api_security.dto.CreateCurrencyDto;
import dev.moon.security.api_security.service.CurrencyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

  @Autowired
  CurrencyService currencyService;

  @PostMapping
  public ResponseEntity<?> createNewCurrency(@RequestBody @Valid CreateCurrencyDto createCurrencyDto) {
    String currencyCode = createCurrencyDto.currencyCode();
    if (currencyService.checkCurrencyExists(currencyCode) != null) {
      return ResponseEntity
              .badRequest()
              .body(Map.of("message", "Currency with this code already exists"));
    }

    String currency = currencyService.createCurrency(currencyCode);

    return ResponseEntity
            .ok()
            .body(Map.of("currency", currency));
  }
}
