package com.rehic.execptions;

public class JwtConfigurationException extends RuntimeException {
    public JwtConfigurationException(String message) {
        super(message);
    }
}