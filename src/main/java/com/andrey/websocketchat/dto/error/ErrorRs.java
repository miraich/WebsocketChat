package com.andrey.websocketchat.dto.error;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Jacksonized
@Builder(toBuilder = true)
public record ErrorRs(
        Map<String, String> errors
) {
}
