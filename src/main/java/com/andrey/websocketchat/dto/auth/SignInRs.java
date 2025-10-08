package com.andrey.websocketchat.dto.auth;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record SignInRs(

        String username,

        String password
) {
}
