package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TimeController {
    private static final Logger logger = LoggerFactory.getLogger(TimeController.class);

    @GetMapping("/getTime")
    public LocalDateTime getTime() {
        logger.info("got timeRequest");
        return LocalDateTime.now();
    }
}
