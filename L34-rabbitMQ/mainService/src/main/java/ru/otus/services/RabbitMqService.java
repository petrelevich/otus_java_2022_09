package ru.otus.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.domain.Client;

@Service
public class RabbitMqService {

    public static final String MAIN_EXCHANGE = "main-exchange";

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendClientCreatedEvent(Client client) {
        rabbitTemplate.convertAndSend("clients.new_created", client);
    }

    public Client sendClientCreatedEventAndWait(Client client) {
        return (Client) rabbitTemplate.convertSendAndReceive("clients.rpc.new_created", client);
    }
}
