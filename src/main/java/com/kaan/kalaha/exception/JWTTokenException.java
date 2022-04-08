package com.kaan.kalaha.exception;

import lombok.Data;

@Data
public class JWTTokenException extends RuntimeException {
    public JWTTokenException(String message) {
        super(message);
    }
}
