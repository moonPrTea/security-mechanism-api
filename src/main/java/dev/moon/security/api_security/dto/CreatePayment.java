package dev.moon.security.api_security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePayment(
        @JsonProperty("card_id")
        UUID cardId,
        @JsonProperty("destination_card_id")
        UUID destinationCardId,
        BigDecimal amount,
        String currency,
        String message
) {

}

