package com.kaan.kalaha.kalaha.exception;


/**
 * JWT Token Exception
 * throws when token is not valid
 */
public class JWTTokenException extends RuntimeException {
    private final String error;

    public JWTTokenException(String error) {
        this.error = error;
    }
}
