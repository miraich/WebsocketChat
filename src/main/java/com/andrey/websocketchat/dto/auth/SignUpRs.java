package com.andrey.websocketchat.dto.auth;

import com.andrey.websocketchat.dto.User.UserRs;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record SignUpRs(
        UserRs user,

        String token
) {
}
