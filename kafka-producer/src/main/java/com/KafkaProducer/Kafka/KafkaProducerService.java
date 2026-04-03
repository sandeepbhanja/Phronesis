package com.KafkaProducer.Kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

	private final KafkaProducer<String, String> producer;

    public KafkaProducerService(KafkaProducer<String, String> producer) {
        this.producer = producer;
    }

    public boolean sendDataToKafka(String body) {

        ProducerRecord<String, String> record =
            new ProducerRecord<>("CustomerCountry", "Precision Products", body);

        try {
            producer.send(record);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
