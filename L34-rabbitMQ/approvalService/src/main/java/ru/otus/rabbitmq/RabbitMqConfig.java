package ru.otus.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.otus.services.RabbitMqService.MAIN_EXCHANGE;

@Configuration
public class RabbitMqConfig {
    @Bean
    public Jackson2JsonMessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonConverter());
        rabbitTemplate.setExchange(MAIN_EXCHANGE);
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(MAIN_EXCHANGE, false, false);
    }

    @Bean
    public Queue newClientsEventsQueue(){
        return QueueBuilder.durable("new-clients-queue")
                .withArgument("x-dead-letter-exchange", "dead-letter-exchange")
                .build();
    }

    @Bean
    public Binding newClientsEventsQueueBinding(){
        return BindingBuilder.bind(newClientsEventsQueue())
                .to(topicExchange())
                .with("clients.new_created");
    }

    @Bean
    public Queue allClientsEventsQueue(){
        return new Queue("all-clients-events-queue");
    }

    @Bean
    public Binding allClientsEventsQueueBinding(){
        return BindingBuilder.bind(allClientsEventsQueue())
                .to(topicExchange())
                .with("clients.#");
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return new FanoutExchange("dead-letter-exchange");
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable("dead-letter-queue").build();
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }

  @Bean
    public Queue newClientsEventsRpcQueue(){
        return new Queue("new-clients-rpc-queue");
    }

    @Bean
    public Binding newClientsEventsRpcQueueBinding(){
        return BindingBuilder.bind(newClientsEventsRpcQueue())
                .to(topicExchange())
                .with("clients.rpc.new_created");
    }
}
