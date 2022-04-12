package com.kaan.kalaha.exception;

public class GameNotFoundException extends RuntimeException{
    private final String error;
    public GameNotFoundException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
