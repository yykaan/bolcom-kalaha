package com.kaan.kalaha.kalaha.exception;

/**
 * GameNotFoundException
 * throws when game not found
 */
public class GameNotFoundException extends RuntimeException{
    private final String error;
    public GameNotFoundException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
