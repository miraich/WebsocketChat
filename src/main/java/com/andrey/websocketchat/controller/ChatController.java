package com.andrey.websocketchat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @GetMapping
    public List<String> getAll() {
        return List.of("Димон", "Димон", "Димон", "Димон", "Димон", "Димон", "Димон", "Димон", "Димон", "Димон");
    }
}