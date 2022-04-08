package com.kaan.kalaha.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorExceptionResponse {
    private Date timestamp = new Date();
    private String message;
    private int status;
}
