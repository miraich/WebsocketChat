package com.andrey.websocketchat.dto.auth;

import com.andrey.websocketchat.dto.user.UserRs;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record SignInRs(
        UserRs user,

        String token
) {
}
