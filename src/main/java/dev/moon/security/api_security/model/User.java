package dev.moon.security.api_security.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public record User(
        UUID id,
        String firstName,
        String secondName,
        String middleName,
        Instant createdAt
) implements Serializable {
}
