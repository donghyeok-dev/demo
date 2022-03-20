package com.example.restapi.global.error.exception;

public class NotFoundDataException extends RuntimeException {
    public NotFoundDataException() {
        super();
    }
    public NotFoundDataException(String message) {
        super(message);
    }
}
