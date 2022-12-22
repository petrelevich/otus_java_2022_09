package ru.otus.neo4jdemo.repository;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import ru.otus.neo4jdemo.model.Phone;
import ru.otus.neo4jdemo.model.PhoneUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Neo4jPhoneUserRepository implements PhoneUserRepository {

    private final Driver driver;
    private final Gson mapper;
    private final PhoneRepository phoneRepository;


    @Override
    public void insert(PhoneUser phoneUser) {
        try (val session = driver.session()) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", phoneUser.getId());
            params.put("name", phoneUser.getName());
            session.run("CREATE (n:PhoneUser {id: $id, name: $name})", params);

            phoneUser.getPhones().forEach(p -> {
                params.clear();
                params.put("userId", phoneUser.getId());
                params.put("phoneId", p.getId());
                session.run("MATCH (u:PhoneUser {id: $userId}), (p:Phone {id: $phoneId}) " +
                        "MERGE (u)-[r:OWN]->(p)", params);
            });

        }
    }

    @Override
    public Optional<PhoneUser> findOne(String id) {
        try (val session = driver.session()) {
            Result result = session.run("MATCH (n:PhoneUser {id: $id}) " +
                    "RETURN \"{id: \" + n.id + \", " +
                    "name: \" + n.name + \"}\" as res", Map.of("id", id));
            return Optional.ofNullable(result.single().get("res"))
                    .map(r -> mapper.fromJson(r.asString(), PhoneUser.class))
                    .map(u -> {
                        List<Phone> phones = phoneRepository.findAllByUserId(id);
                        u.setPhones(phones);
                        return u;
                    });
        }
    }

    @Override
    public List<PhoneUser> findAll() {
        try (val session = driver.session()) {
            Result result = session.run("MATCH (n:PhoneUser) " +
                    "RETURN \"{id: \" + n.id + \", " +
                    "name: \" + n.name + \"}\" as res");
            List<PhoneUser> users = result.list().stream()
                    .map(r -> mapper.fromJson(r.get("res").asString(), PhoneUser.class))
                    .collect(Collectors.toList());

            users.forEach(u -> {
                u.setPhones(phoneRepository.findAllByUserId(u.getId()));
            });
            return users;
        }
    }
}
