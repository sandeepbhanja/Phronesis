package com.KafkaProducer.Kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {

	@Bean
    public KafkaProducer<String, String> kafkaProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers","broker1:9092,broker2:9092");
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer<>(props);
    }
	
}