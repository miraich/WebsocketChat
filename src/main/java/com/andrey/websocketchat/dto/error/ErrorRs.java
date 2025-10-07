package com.andrey.websocketchat.dto.error;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record ErrorRs(
        String message
) {
}
