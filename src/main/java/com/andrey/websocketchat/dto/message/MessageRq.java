package com.andrey.websocketchat.dto.message;



import java.util.UUID;

public record MessageRq(
        UUID id,
        UUID chatId,
        String from,
        String content
) {
}


//@Jacksonized
//@Builder(toBuilder = true)
//public record MessageRq(
//        @NotNull UUID roomId,
//        @NotBlank String from,
//        @NotBlank String content
//) {
//}