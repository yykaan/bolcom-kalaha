package com.kaan.kalaha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {JWTTokenException.class})
    public ResponseEntity<JWTExceptionResponse> handleJWTTokenException(JWTExceptionResponse ex) {
        JWTExceptionResponse response = new JWTExceptionResponse();
        response.setTokenAvailable(false);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
