package dev.moon.security.api_security.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


// basic fields, just for example
public record Card(
        UUID cardUUID,
        UUID userId,
        BigDecimal balance,
        String currency,
        Instant createdAt,
        Instant updatedAt

) {
}
