package ru.otus.mongodemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Phone {

    @BsonId
    private ObjectId id;

    @BsonProperty()
    private String model;

    @BsonProperty
    private String color;

    @BsonProperty
    private String serialNumber;
}
