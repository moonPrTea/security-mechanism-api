package dev.moon.security.api_security.dto;

public record CreateUserDto(
        String firstName,
        String secondName,
        String middleName
) {
}
