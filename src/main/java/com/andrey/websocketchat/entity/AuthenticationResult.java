package com.andrey.websocketchat.entity;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record AuthenticationResult(User user, String token) {
}
