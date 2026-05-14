package dev.moon.security.api_security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserRecord(
        UUID id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("second_name")
        String secondName,
        @JsonProperty("middle_name")
        String middleName,
        String username,
        @JsonProperty("created_at")
        LocalDateTime createdAt
) {
}
