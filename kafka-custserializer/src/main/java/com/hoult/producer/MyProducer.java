package com.hoult.producer;

import com.hoult.serizlizer.UserSerializer;
import entity.User;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;

public class MyProducer {
    public static void main(String[] args) {
        HashMap<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "linux121:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserSerializer.class);

        KafkaProducer<String, User> producer = new KafkaProducer<String, User>(configs);

        User user = new User();
        user.setUserId(1001);
        user.setUsername("hoult");

        ProducerRecord<String, User> record = new ProducerRecord<>("topic_user_id", user.getUsername(), user);

        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null) {
                    System.out.println("消息异常发送");
                } else {
                    System.out.println("主题： " + recordMetadata.topic()
                    + "\t,分区：" + recordMetadata.partition()
                    + "\t,偏移：" + recordMetadata.offset());
                }
            }
        });

        producer.close();
    }
}
