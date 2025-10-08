package com.andrey.websocketchat.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @GetMapping
    public String getAll(Authentication auth) {
        System.out.println(auth.getName());
        auth.getAuthorities().forEach(System.out::println);
        return null;
//        return List.of("Димон", "Димон", "Димон", "Димон", "Димон", "Димон", "Димон", "Димон", "Димон", "Димон");
    }
}
