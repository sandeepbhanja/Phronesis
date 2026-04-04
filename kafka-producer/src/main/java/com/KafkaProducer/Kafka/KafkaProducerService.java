package com.KafkaProducer.Kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import com.KafkaProducer.DTO.LogDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaProducerService {

    private final KafkaProducer<String, String> producer;
    private static final ObjectMapper mapper = new ObjectMapper();

    public KafkaProducerService(KafkaProducer<String, String> producer) {
        this.producer = producer;
    }

    public boolean sendDataToKafka(LogDTO log) {
        try {
            String json = mapper.writeValueAsString(log);
            ProducerRecord<String, String> record = new ProducerRecord<>("logs", "Precision Products", json);
            try {
                producer.send(record);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
