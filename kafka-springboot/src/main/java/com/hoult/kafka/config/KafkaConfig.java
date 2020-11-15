package com.hoult.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic topic1() {
        return new NewTopic("ntpc-01", 3, (short)1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic("ntpc-02", 3, (short)1);
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<String, Object>();
        configs.put("bootstrap.servers", "linux121:9092");
        KafkaAdmin admin = new KafkaAdmin(configs);
        return admin;
    }


    public KafkaTemplate<Integer, String> kafkaTemplate(ProducerFactory<Integer, String> producerFactory) {

        //覆盖默认的producerFactory参数
        HashMap<String, Object> configOverride = new HashMap<>();
        configOverride.put(ProducerConfig.BATCH_SIZE_CONFIG, 200);

        KafkaTemplate<Integer, String> tem = new KafkaTemplate<Integer, String>(producerFactory, configOverride);

        return tem;
    }

}
