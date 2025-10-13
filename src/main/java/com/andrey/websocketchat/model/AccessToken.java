package com.andrey.websocketchat.model;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record AccessToken(
        String accessToken
) {
}
