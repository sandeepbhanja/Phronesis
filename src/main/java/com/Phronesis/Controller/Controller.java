package com.Phronesis.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Phronesis.Repository.RedisService;
import com.Phronesis.Service.ServiceImplementation;

@RestController
public class Controller {

    private final ServiceImplementation service;
    private final RedisService redisService;

    @Autowired
    public Controller(ServiceImplementation service, RedisService redisService) {
        this.service = service;
        this.redisService = redisService;
    }

    @PostMapping("/saveDataToRedis")
    public ResponseEntity<Map<String, Object>> saveDataToRedis(@RequestParam String body) throws Exception {
        boolean isRedisDataSet = redisService.setRedisData(body);
        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("success", isRedisDataSet);
        return new ResponseEntity<>(responseObject, HttpStatus.ACCEPTED);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> getHealthStatus() throws Exception {

        boolean isHealthy = service.checkHealth();

        if (!isHealthy) {
            throw new Exception("Not Healthy");
        }

        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("success", true);
        responseObject.put("status", "UP");
        return new ResponseEntity<>(responseObject, HttpStatus.ACCEPTED);

    }
}
