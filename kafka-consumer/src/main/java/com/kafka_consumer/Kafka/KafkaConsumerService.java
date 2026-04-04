package com.kafka_consumer.Kafka;

import java.time.Duration;
import java.util.Collections;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka_consumer.DTO.LogDTO;

@Service
public class KafkaConsumerService {

    private final KafkaConsumer<String, String> consumer;
    private static final ObjectMapper mapper = new ObjectMapper();

    public KafkaConsumerService(KafkaConsumer<String, String> consumer)
            throws JsonMappingException, JsonProcessingException {
        this.consumer = consumer;
        this.consumer.subscribe(Collections.singletonList("logs"));

        Duration timeout = Duration.ofMillis(100);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(timeout);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("""
                        topic = %s, partition = %d, offset = %d, customer = %s, country = %s
                        """,
                        record.topic(), record.partition(), record.offset(),
                        record.key(), record.value());
                LogDTO log = mapper.readValue(record.value(), LogDTO.class);
                consumer.commitAsync();
                System.out.println("Log Data = " + log);
            }
        }

    }

}
