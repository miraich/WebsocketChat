package com.andrey.websocketchat.controller;

import com.andrey.websocketchat.dto.message.MessageRq;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageRq greeting(MessageRq message) throws Exception {
        return message;
    }

}
