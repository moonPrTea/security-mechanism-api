package dev.moon.security.api_security.model;

import java.io.Serializable;
import java.time.Instant;

public record User(
        Integer id,
        String firstName,
        String secondName,
        String middleName,
        Instant createdAt
) implements Serializable {
}
