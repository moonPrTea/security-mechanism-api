package dev.moon.security.api_security.model;

import java.util.UUID;

public record OperationType(
        UUID typeId,
        String text
) {
}
