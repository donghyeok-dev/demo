package com.example.restapi.global.error.exception;

import com.example.restapi.global.error.ErrorCode;

public class NotFoundDataException extends RuntimeException {
    public NotFoundDataException() {
        super();
    }
    public NotFoundDataException(String message) {
        super(message);
    }
}
