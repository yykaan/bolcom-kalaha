package com.kaan.kalaha.exception;

/**
 * NoStoneToSowException
 * thrown when there is no stone to sow
 */
public class NoStoneToSowException extends RuntimeException{
    private final String error;
    public NoStoneToSowException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
