package com.example.oauthjwt.config.oauth2.exceptions;

public class OAuth2ProviderMissMatchException extends RuntimeException{

    public OAuth2ProviderMissMatchException(String message) {
        super(message);
    }
}
