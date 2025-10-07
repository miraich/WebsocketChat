package com.andrey.websocketchat.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record SignInRq(
        @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
        @NotBlank(message = "Имя пользователя не может быть пустыми")
        String username,

        @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
        @NotBlank(message = "Пароль не может быть пустыми")
        String password
) {
}
