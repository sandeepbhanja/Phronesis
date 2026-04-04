package com.KafkaProducer.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.KafkaProducer.DTO.LogDTO;
import com.KafkaProducer.Kafka.KafkaProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class KafkaProducerController {

	@Autowired
	KafkaProducerService kafkaService;
	private static final ObjectMapper mapper = new ObjectMapper();

	@PostMapping("/sendData")
	ResponseEntity<Map<String, Object>> sendDataToKafka(@RequestBody String body)
			throws JsonMappingException, JsonProcessingException {

		LogDTO logDTO = mapper.readValue(body, LogDTO.class);
		boolean isDataSent = kafkaService.sendDataToKafka(logDTO);
		if (isDataSent) {
			return new ResponseEntity<>(Map.of("success", true), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("success", false), HttpStatus.BAD_GATEWAY);
		}
	}

}
