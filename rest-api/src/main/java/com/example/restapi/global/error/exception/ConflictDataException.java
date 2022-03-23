package com.example.restapi.global.error.exception;

public class ConflictDataException extends RuntimeException {
    public ConflictDataException() {
        super();
    }
    public ConflictDataException(String message) {
        super(message);
    }
}
