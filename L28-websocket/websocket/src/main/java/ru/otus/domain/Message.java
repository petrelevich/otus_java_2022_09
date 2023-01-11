package ru.otus.domain;

public class Message {
    private String messageStr;

    public Message() {
    }

    public Message(String messageStr) {
        this.messageStr = messageStr;
    }

    public String getMessageStr() {
        return messageStr;
    }

    public void setMessageStr(String messageStr) {
        this.messageStr = messageStr;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageStr='" + messageStr + '\'' +
                '}';
    }
}
