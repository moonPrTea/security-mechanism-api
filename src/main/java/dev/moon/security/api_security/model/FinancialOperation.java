package dev.moon.security.api_security.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record FinancialOperation(
        UUID operationId,
        UUID cardId,
        OperationType operationType,
        BigDecimal amount,
        Instant createdAt
) {
}
