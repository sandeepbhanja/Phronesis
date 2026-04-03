package com.KafkaProducer.Controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.KafkaProducer.Kafka.KafkaProducerService;

@Controller
public class KafkaProducerController {
	
	KafkaProducerService kafkaService;
	
	KafkaProducerController(KafkaProducerService kafkaService){
		this.kafkaService = kafkaService;
	}
	
	@PostMapping("/sendData")
	ResponseEntity<Map<String,Object>> sendDataToKafka(@RequestBody String body){
		boolean isDataSent = kafkaService.sendDataToKafka(body);
		if(isDataSent) {
			return new ResponseEntity<Map<String,Object>>(Map.of("success",true),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Map<String,Object>>(Map.of("success",false),HttpStatus.BAD_GATEWAY);
		}
	}
	
}
