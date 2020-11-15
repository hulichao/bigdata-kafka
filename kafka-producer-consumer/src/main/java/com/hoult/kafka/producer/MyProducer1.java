package com.hoult.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MyProducer1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Map<String, Object> configs = new HashMap<String, Object>();
        // 指定初始连接用到的broker地址
        configs.put("bootstrap.servers", "linux121:9092");
        // 指定key的序列化类
        configs.put("key.serializer", IntegerSerializer.class);
        // 指定value的序列化类
        configs.put("value.serializer", StringSerializer.class);

//        configs.put("acks", "all");
//        configs.put("reties", "3");

        KafkaProducer<Integer, String> producer = new KafkaProducer<Integer, String>(configs);

        // 用于设置用户自定义的消息头字段
        List<Header> headers = new ArrayList<Header>();
        headers.add(new RecordHeader("biz.name", "producer.demo".getBytes()));

        ProducerRecord<Integer, String> record = new ProducerRecord<Integer, String>(
                "topic_1",
                1,
                1,
                "ss_" + new Random().nextInt(100),
                headers
        );

        // 消息的同步确认
        final Future<RecordMetadata> future = producer.send(record);
        final RecordMetadata metadata = future.get();
        System.out.println("消息的主题：" + metadata.topic());
        System.out.println("消息的分区号：" + metadata.partition());
        System.out.println("消息的偏移量：" + metadata.offset());

        // 消息的异步确认
//        producer.send(record, new Callback() {
//            public void onCompletion(RecordMetadata metadata, Exception exception) {
//                if (exception == null) {
//                    System.out.println("消息的主题：" + metadata.topic());
//                    System.out.println("消息的分区号：" + metadata.partition());
//                    System.out.println("消息的偏移量：" + metadata.offset());
//                } else {
//                    System.out.println("异常消息：" + exception.getMessage());
//                }
//            }
//        });

        // 关闭生产者
        producer.close();
    }}
