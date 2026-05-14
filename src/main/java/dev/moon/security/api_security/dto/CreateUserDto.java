package dev.moon.security.api_security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(
        @NotBlank @JsonProperty("first_name")
        String firstName,
        @NotBlank @JsonProperty("second_name")
        String secondName,
        @JsonProperty("middle_name")
        String middleName,
        @NotBlank String username,
        @NotBlank String password
) {
}
