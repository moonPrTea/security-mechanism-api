package dev.moon.security.api_security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCurrencyDto(
        @JsonProperty("currency_code")
        String currencyCode
) {
}
