package com.andrey.websocketchat.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Jacksonized
@Builder(toBuilder = true)
public record MessageRq(
        @NotNull UUID roomId,
        @NotBlank String from,
        @NotBlank String content
) {
}