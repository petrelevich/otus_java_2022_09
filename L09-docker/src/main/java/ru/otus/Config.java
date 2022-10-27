package ru.otus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.database.JdbcDemo;

@Configuration
public class Config {

    @Bean
    public JdbcDemo jdbcDemo(@Value("${database.url}") String url,
                             @Value("${database.user}") String user,
                             @Value("${database.pwd}") String pwd) {
        return new JdbcDemo(url, user, pwd);
    }
}
