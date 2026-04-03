package com.Phronesis.util;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.Phronesis.Entity.ErrorMessage;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> generalizedError(Exception ex, HttpServletRequest request) {
        LocalDate currentDate = LocalDate.now();

        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 400, currentDate);

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
