package com.kaan.kalaha.exception;

/**
 * UserNotLoggedInException
 * throws when user is not logged in
 */
public class UserNotLoggedInException extends RuntimeException{
    private final String error;
    public UserNotLoggedInException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
