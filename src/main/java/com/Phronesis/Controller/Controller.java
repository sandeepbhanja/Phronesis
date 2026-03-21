package com.Phronesis.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Phronesis.Service.ServiceImplementation;

@RestController
public class Controller {

    private ServiceImplementation service;

    @Autowired
    public Controller(ServiceImplementation service) {
        this.service = service;
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
