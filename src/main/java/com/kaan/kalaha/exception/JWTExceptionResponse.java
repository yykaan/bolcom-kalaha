package com.kaan.kalaha.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JWTExceptionResponse extends ErrorExceptionResponse {
    @JsonProperty("isTokenAvailable")
    private boolean isTokenAvailable;
}
