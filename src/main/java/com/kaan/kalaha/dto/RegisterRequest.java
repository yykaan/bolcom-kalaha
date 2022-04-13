package com.kaan.kalaha.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * RegisterRequest is used by the client to register a new player.
 * {@link RegisterRequest#username} is the name of the player and must be unique
 * {@link RegisterRequest#password} is the password of the player.
 * {@link RegisterRequest#email} is the email of the player and must be unique
 * used in {@link com.kaan.kalaha.controller.AuthController#registerUser(RegisterRequest)}
 */
@Getter
@Setter
public class RegisterRequest implements Serializable {
    private String username;
    private String password;
    private String email;
}
