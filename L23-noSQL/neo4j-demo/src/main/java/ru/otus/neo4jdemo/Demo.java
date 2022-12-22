package ru.otus.neo4jdemo;

import com.google.gson.Gson;
import lombok.val;
import org.neo4j.driver.*;
import ru.otus.neo4jdemo.model.Phone;
import ru.otus.neo4jdemo.model.PhoneUser;
import ru.otus.neo4jdemo.repository.Neo4jPhoneRepository;
import ru.otus.neo4jdemo.repository.Neo4jPhoneUserRepository;
import ru.otus.neo4jdemo.repository.PhoneRepository;
import ru.otus.neo4jdemo.repository.PhoneUserRepository;

import java.util.*;
import java.util.stream.Collectors;


public class Demo {
    private static final int NEO4J_PORT = 7687;
     private static final String NEO4J_HOST = "localhost"; // Работа без DockerToolbox
    //private static final String NEO4J_HOST = "192.168.99.100"; // Работа через DockerToolbox
    private static final String NEO4J_URL = "bolt://" + NEO4J_HOST + ":" + NEO4J_PORT;

    public static void main(String[] args) throws Throwable {
        val motorolaC350 = new Phone(UUID.randomUUID().toString(),
                "C350", "silver", "000001");
        val sonyEricssonZ800i = new Phone(UUID.randomUUID().toString(),
                "Z800i", "silver", "000002");
        val huaweiP20 = new Phone(UUID.randomUUID().toString(),
                "p20", "black", "000003");

        val vasya = new PhoneUser(UUID.randomUUID().toString(),
                "Vasya", List.of(motorolaC350, sonyEricssonZ800i));
        val anya = new PhoneUser(UUID.randomUUID().toString(),
                "Anya", List.of(huaweiP20));

        val mapper = new Gson();
        //try (val driver = GraphDatabase.driver(NEO4J_URL, AuthTokens.basic("neo4j", "neo4j"))) {
        try (val driver = GraphDatabase.driver(NEO4J_URL)) {
            dropAllNodes(driver);

            PhoneRepository phoneRepository = new Neo4jPhoneRepository(driver, mapper);
            PhoneUserRepository phoneUserRepository = new Neo4jPhoneUserRepository(driver, mapper, phoneRepository);

            // Результат можно смотреть в http://localhost:7474/browser/
            phoneRepository.insert(motorolaC350);
            phoneRepository.insert(sonyEricssonZ800i);
            phoneRepository.insert(huaweiP20);

            phoneUserRepository.insert(vasya);
            phoneUserRepository.insert(anya);

            System.out.println("\n");

            Optional<Phone> motorolaZ800iOptional = phoneRepository.findOne(sonyEricssonZ800i.getId());
            motorolaZ800iOptional.ifPresent(p -> System.out.printf("Phone from db is:\n%s", p));

            System.out.println("\n");

            List<Phone> allPhones = phoneRepository.findAll();
            System.out.println("All phones from db:\n" + allPhones.stream()
                    .map(Objects::toString).collect(Collectors.joining("\n")));

            System.out.println("\n");

            Optional<PhoneUser> vasyaOptional = phoneUserRepository.findOne(vasya.getId());
            vasyaOptional.ifPresent(u -> System.out.printf("User from db is:\n%s", u));

            System.out.println("\n");

            List<PhoneUser> allPhoneUsers = phoneUserRepository.findAll();
            System.out.println("All users from db:\n" + allPhoneUsers.stream()
                    .map(Objects::toString).collect(Collectors.joining("\n")));

            System.out.println("\n");

        }
    }


    private static void dropAllNodes(Driver driver) {
        try (val session = driver.session()) {
            session.run("MATCH (n) DETACH DELETE n");
        }
    }
}
