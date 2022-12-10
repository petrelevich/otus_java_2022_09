package ru.otus.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JacksonDemo {
    private final ObjectMapper mapper = new ObjectMapper();
    private final User user = new User(32, "Jack", "anyData");

    public static void main(String[] args) throws IOException {

        var demo = new JacksonDemo();
        var fileName = "jack.json";
        demo.save(fileName);
        demo.loadUser(fileName);

        var userAsString = demo.writeAsString();
        System.out.println("userAsString:" + userAsString);

        var userFromString = demo.readFromString(userAsString);
        System.out.println("user from string:" + userFromString);

        var propertyValue = demo.readPropValue(userAsString, "nameForSerialization");
        System.out.println("propertyValue:" + propertyValue);
    }

    private void save(String fileName) throws IOException {
        var file = new File(fileName);
        mapper.writeValue(file, user);
        System.out.println("user saved to the file:" + file.getAbsolutePath());
    }

    private void loadUser(String fileName) throws IOException {
        var file = new File(fileName);
        var userLoaded = mapper.readValue(file, User.class);
        System.out.println("user loaded from the file:" + file.getAbsolutePath() + ", user:" + userLoaded);
    }

    private String writeAsString() throws JsonProcessingException {
        return mapper.writeValueAsString(user);
    }

    private User readFromString(String userAsString) throws JsonProcessingException {
        return mapper.readValue(userAsString, User.class);

    }

    private String readPropValue(String userAsString, String nameForSerialization) throws JsonProcessingException {
        JsonNode node = mapper.readTree(userAsString);
        JsonNode jsonProp = node.get(nameForSerialization);
        return jsonProp.textValue();
    }
}
