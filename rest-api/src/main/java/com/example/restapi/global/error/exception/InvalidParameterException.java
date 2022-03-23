package com.example.restapi.global.error.exception;

import org.springframework.validation.Errors;

public class InvalidParameterException extends RuntimeException {
    // https://meetup.toast.com/posts/147
    private Errors errors;

    public InvalidParameterException() {
        super();
    }
    public InvalidParameterException(String message) {
        super(message);
    }
    public InvalidParameterException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }
}
