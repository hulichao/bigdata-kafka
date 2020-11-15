package com.hoult.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyConsumer {

    @KafkaListener(topics = "topic-spring-01")
    public void onMessage(ConsumerRecord<Integer, String> record) {
        System.out.println("消费者收到的消息: "
                + record.topic() + "\t"
                + record.key() + "\t"
                + record.offset()
                + "\t" + record.value());
    }
}
