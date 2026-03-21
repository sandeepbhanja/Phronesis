package com.Phronesis.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> getHealthStatus(@RequestParam boolean isHealthy) throws Exception {

        Map<String, Object> responseObject = new HashMap<>();

        if (isHealthy) {
            responseObject.put("success", true);
            return new ResponseEntity<>(responseObject, HttpStatus.ACCEPTED);
        }

        else {
            throw new Exception("Not Healthy");
        }
    }

}
