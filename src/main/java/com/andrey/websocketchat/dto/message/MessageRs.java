package com.andrey.websocketchat.dto.message;


import java.time.Instant;
import java.util.UUID;

public record MessageRs(
        UUID id,
        UUID chatId,
        String from,
        String content,
        Instant timestamp
) {
}



