package com.Phronesis.Entity;

import java.time.LocalDate;

public class ErrorMessage {

    private String message;
    private int statusCode;
    private LocalDate timestamp;

    public ErrorMessage(String message, int statusCode, LocalDate timestamp) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public LocalDate getTimeStamp() {
        return timestamp;
    }

}
