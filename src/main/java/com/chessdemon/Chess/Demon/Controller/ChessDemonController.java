package com.chessdemon.Chess.Demon.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChessDemonController {
    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/ping")
    public String ping() {
        return "pong " + appName;
    }
}
