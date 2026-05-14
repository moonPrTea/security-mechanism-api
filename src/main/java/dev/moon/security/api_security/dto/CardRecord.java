package dev.moon.security.api_security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CardRecord(
        UUID id,
        BigDecimal balance,
        @JsonProperty("created_at")
        LocalDateTime createdAt
) {
}
