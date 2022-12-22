package ru.otus.mongodemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmartPhone {
    @BsonId
    private ObjectId id;
    private String model;
    private String color;
    private String serialNumber;

    private String operatingSystem;
}
