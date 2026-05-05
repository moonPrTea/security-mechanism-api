package dev.moon.security.api_security.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(
        @NotBlank String firstName,
        @NotBlank String secondName,
        String middleName,
        @NotBlank String username,
        @NotBlank String password
) {
}
