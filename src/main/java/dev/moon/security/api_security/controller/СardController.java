package dev.moon.security.api_security.controller;

import dev.moon.security.api_security.dto.CardRecord;
import dev.moon.security.api_security.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class СardController {

  @Autowired
  CardService cardService;

  @PostMapping
  public CardRecord createNewCardApplication() {
    return cardService.createCard();
  }
}
