package com.kaan.kalaha.kalaha.exception;

/**
 * AuthenticationException
 * throws when authentication fails
 */
public class AuthenticationException extends RuntimeException{
    private final String error;
    public AuthenticationException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
