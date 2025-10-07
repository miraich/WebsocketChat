package com.andrey.websocketchat.dto.User;

import java.util.UUID;

public record UserRs(
        UUID id,
        String username,
        String role
) {

}
