package com.kaan.kalaha.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * LoginRequest is used by the client to login to the server.
 * {@link LoginRequest#username} and {@link LoginRequest#password} are used to authenticate the user.
 * used in {@link com.kaan.kalaha.controller.AuthController#authenticateUser(LoginRequest)}
 */
@Getter
@Setter
public class LoginRequest implements Serializable {
    private String username;
    private String password;
}
