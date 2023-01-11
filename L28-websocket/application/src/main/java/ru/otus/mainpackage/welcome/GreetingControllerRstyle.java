package ru.otus.mainpackage.welcome;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("${application.rest.api.prefix}/v1")
public class GreetingControllerRstyle {
    private static final Logger logger = LoggerFactory.getLogger(GreetingControllerRstyle.class);

    private final GreetingService greetingService;

    public GreetingControllerRstyle(GreetingService greetingService) {
        logger.info("Я НАСТОЯЩИЙ БИН!!!");
        this.greetingService = greetingService;
    }

    //http://localhost:8080/api/v1/hello/jone
    @Operation(summary = "Пример документирования Endpoint")
    @GetMapping(value = "/hello/{name}")
    public Map<String, String> sayHello(
            @Parameter(description = "Описание параметра")
            @PathVariable("name") String name) {
        return this.greetingService.sayHello(name);
    }
}
