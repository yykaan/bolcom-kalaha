package com.kaan.kalaha.service;

import com.kaan.kalaha.dto.LoginRequest;
import com.kaan.kalaha.dto.RegisterRequest;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.security.model.SecurityUser;

public interface AuthService {

    boolean isPasswordTrue(LoginRequest loginRequest);

    SecurityUser loadUserByUserName(String username);

    SecurityUser register(RegisterRequest registerRequest);

    KalahaPlayer getCurrentUser(String username);
}
