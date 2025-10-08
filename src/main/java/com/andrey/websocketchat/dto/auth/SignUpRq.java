package com.andrey.websocketchat.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record SignUpRq(
        @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
        @NotBlank(message = "Username cannot be empty")
        String username,

        @Size(max = 255, message = "Password length must not exceed 255 characters")
        @NotBlank(message = "Password cannot be empty")
        String password
) {
}
