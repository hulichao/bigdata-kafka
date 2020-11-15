package com.hoult.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;

public class MyProducer {
    public static void main(String[] args) {

        HashMap<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "linux121:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPatitioner.class);
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(configs);

        ProducerRecord<String, String> record = new ProducerRecord<String, String>(
                "to_part_01",
                "mykey",
                "myvalue");

        kafkaProducer.send(record, new Callback() {
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

        kafkaProducer.close();
    }
}
