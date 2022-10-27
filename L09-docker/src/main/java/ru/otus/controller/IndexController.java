package ru.otus.controller;

import java.sql.SQLException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import ru.otus.database.JdbcDemo;


@RestController
public class IndexController {

    private final JdbcDemo jdbcDemo;

    public IndexController(JdbcDemo jdbcDemo) {
        this.jdbcDemo = jdbcDemo;
    }

    @GetMapping("/hi")
    public String hi(@RequestParam(name="name") String name) throws UnknownHostException {
        return String.format("Hi, %s. It works, host: %s", name, InetAddress.getLocalHost().getHostName());
    }

    @PostMapping("/response/{name}")
    public Response response(@PathVariable("name") String name, @RequestBody Request params)  {
        return new Response(name, String.format("%s-%s", params.param1(), params.param2()));
    }

    @GetMapping("/jdbcDemo")
    public long jdbcDemo() throws SQLException {
        return jdbcDemo.exec();
    }
}