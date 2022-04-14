package com.kaan.kalaha.kalaha.service;

import com.kaan.kalaha.dto.LoginRequest;
import com.kaan.kalaha.dto.RegisterRequest;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.exception.AuthenticationException;
import com.kaan.kalaha.security.model.SecurityUser;

import java.util.Map;

/**
 * Responsible for authentication and authorization.
 */
public interface AuthService {

    /**
     * @param loginRequest the login request
     * @return boolean {@code true} if the user is authenticated, {@code false} otherwise
     */
    boolean isPasswordTrue(LoginRequest loginRequest);

    /**
     * @param securityUser the security user
     * @return String {@code JWT Token}
     */
    String generateJwtToken(SecurityUser securityUser);

    /**
     * Returns user with given username
     * @param username the username
     * @return {@link SecurityUser}
     */
    SecurityUser loadUserByUserName(String username);

    /**
     * Register new user with given register request {@link RegisterRequest}
     * @param registerRequest the register request
     * @return {@link SecurityUser}
     */
    SecurityUser register(RegisterRequest registerRequest);

    /**
     * Returns currently logged-in {@link KalahaPlayer}
     * @return {@link KalahaPlayer}
     */
    KalahaPlayer getCurrentUser();

    /**
     * @param loginRequest  {@link LoginRequest} the login request
     * @return {@link Map} of "token" as {@link String} key and "opaque token" as {@link String} value if successful
     * @throws AuthenticationException if the login request is invalid
     */
    Map<String, Object> authenticateUser(LoginRequest loginRequest);
}
