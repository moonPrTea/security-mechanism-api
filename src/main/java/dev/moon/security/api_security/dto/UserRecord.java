package dev.moon.security.api_security.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserRecord(
        UUID id,
        String firstName,
        String secondName,
        String middleName,
        String username,
        LocalDateTime createdAt
) {
}
