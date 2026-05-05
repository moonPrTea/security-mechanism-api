package dev.moon.security.api_security.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CardRecord(
        UUID id,
        BigDecimal balance,
        LocalDateTime createdAt
) {
}
