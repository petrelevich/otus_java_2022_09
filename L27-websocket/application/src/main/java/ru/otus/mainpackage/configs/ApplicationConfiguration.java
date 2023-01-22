package ru.otus.mainpackage.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AppConfigForConfigProps.class)
public class ApplicationConfiguration {

    @Bean
    AppConfigForBean messageConfig(@Value("${application.param-name}") String param) {
        return new AppConfigForBean(param);
    }
}
