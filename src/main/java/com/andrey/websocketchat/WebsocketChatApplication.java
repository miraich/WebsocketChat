package com.andrey.websocketchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WebsocketChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketChatApplication.class, args);
    }
}
