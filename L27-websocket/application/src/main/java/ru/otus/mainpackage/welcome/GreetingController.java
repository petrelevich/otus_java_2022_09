package ru.otus.mainpackage.welcome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.mainpackage.configs.AppConfigForConfigProps;
import ru.otus.mainpackage.configs.AppConfigForBean;


import java.util.Map;

@RestController
@RequestMapping("${application.rest.api.prefix}/v1")
public class GreetingController {
    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);
    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService, AppConfigForConfigProps props,
                              @Qualifier("messageConfig") AppConfigForBean appConfigForBean) {
        this.greetingService = greetingService;
        logger.info("ATTENTION! props.getParamName(): {}", props.getParamName());
        logger.info("ATTENTION! applicationConfig.getMessage(): {}", appConfigForBean.getParamName());
    }

    //http://localhost:8080/api/v1/hello?name=ddd
    @GetMapping("/hello")
    public Map<String, String> sayHello(@RequestParam(name="name") String name) {
        return this.greetingService.sayHello(name);
    }
}
