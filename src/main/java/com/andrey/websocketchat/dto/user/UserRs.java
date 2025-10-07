package com.andrey.websocketchat.dto.user;

import java.util.UUID;

public record UserRs(
        UUID id,
        String username,
        String role
) {

}
