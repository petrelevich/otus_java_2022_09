package ru.petrelevich.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.PersistenceCreator;
import reactor.util.annotation.NonNull;

@Table("message")
public class Message {

    @Id
    private final Long id;

    @NonNull
    private final String roomId;

    @NonNull
    private final String msgText;

    @PersistenceCreator
    public Message(Long id, @NonNull String roomId, @NonNull String msgText) {
        this.id = id;
        this.roomId = roomId;
        this.msgText = msgText;
    }

    public Message(@NonNull String roomId, @NonNull String msgText) {
        this(null, roomId, msgText);
    }

    public Long getId() {
        return id;
    }

    @NonNull
    public String getRoomId() {
        return roomId;
    }

    @NonNull
    public String getMsgText() {
        return msgText;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", roomId='" + roomId + '\'' +
                ", msgText='" + msgText + '\'' +
                '}';
    }
}
