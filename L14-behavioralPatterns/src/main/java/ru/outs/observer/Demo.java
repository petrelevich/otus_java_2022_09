package ru.outs.observer;

public class Demo {
    public static void main(String[] args) {
        var producer = new EventProducer();
        var consumerA = new ConsumerA();
        var consumerB = new ConsumerB();

        producer.addListener(consumerA);
        producer.addListener(consumerB.getListener());

        producer.event("eventA");
        producer.event("eventB");

        //Критически важно удалять, когда не нужны
        producer.removeListener(consumerA);
        producer.removeListener(consumerB.getListener());

        producer.event("eventC");
        producer.event("eventD");
    }
}
