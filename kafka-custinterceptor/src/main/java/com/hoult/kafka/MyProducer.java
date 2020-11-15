package com.hoult.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;

public class MyProducer {
    public static void main(String[] args) {
        HashMap<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "linux121:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Integer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "");
        configs.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "com.hoult.kafka.InterceptorOne," +
                "com.hoult.kafka.InterceptorTwo," +
                "com.hoult.kafka.InterceptorThree");


        configs.put("classContent", "this is lagou's kafka class");
        KafkaProducer<Integer, String> producer = new KafkaProducer<Integer, String>(configs);

        ProducerRecord<Integer, String> record = new ProducerRecord<Integer, String>("tp_inter_01", 1001, "good boy ,1001 message");

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
