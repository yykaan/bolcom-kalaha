package com.kaan.kalaha.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
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
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
}
