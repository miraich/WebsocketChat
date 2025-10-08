package com.andrey.websocketchat.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @GetMapping
    public List<String> getAll(@AuthenticationPrincipal Jwt jwt) {
        System.out.println(jwt.getSubject());
        String s = jwt.getClaim("scope");
        String s2 = jwt.getClaim("type");
        System.out.println(s);
        System.out.println(s2);
        return List.of("Димон", "Димон", "Димон", "Димон", "Димон", "Димон", "Димон", "Димон", "Димон", "Димон");
    }
}
