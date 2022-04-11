package com.kaan.kalaha.controller;

import com.kaan.kalaha.config.cache.CacheManager;
import com.kaan.kalaha.dto.LoginRequest;
import com.kaan.kalaha.dto.RegisterRequest;
import com.kaan.kalaha.security.model.SecurityUser;
import com.kaan.kalaha.security.util.JwtUtil;
import com.kaan.kalaha.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final CacheManager cacheManager;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        log.info("Login request received: {}", loginRequest.getUsername());
        boolean isValid = authService.isPasswordTrue(loginRequest);
        if (isValid){
            log.info("Login request successful: {}", loginRequest.getUsername());
            String previousAuthToken = cacheManager.getValue(loginRequest.getUsername());

            if (previousAuthToken != null) {
                log.info("Previous auth token found for user: {}", loginRequest.getUsername());
                cacheManager.delete(loginRequest.getUsername());
                cacheManager.delete(previousAuthToken);
                log.info("Previous auth token deleted for user: {}", loginRequest.getUsername());
            }

            SecurityUser securityUser = authService.loadUserByUserName(loginRequest.getUsername());

            Map<String, Object> response = new HashMap<>();

            String jwt = JwtUtil.generateToken(securityUser);
            String opaqueToken = UUID.randomUUID().toString();

            cacheManager.save(opaqueToken, jwt);
            cacheManager.save(loginRequest.getUsername(), opaqueToken);
            log.info("******************************************************************************************");
            log.info(opaqueToken);
            log.info(jwt);
            log.info("******************************************************************************************");

            log.info("JWT generated for user {}", securityUser.getUsername());

            response.put("token", opaqueToken);
            return ResponseEntity.ok(response);
        }else {
            log.error("Login request failed: {}", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<SecurityUser> registerUser(@RequestBody RegisterRequest registerRequest) {
        log.info("Register request received: {}", registerRequest.getUsername());
        SecurityUser securityUser = authService.register(registerRequest);
        if (securityUser != null) {
            log.info("Register request successful: {}", registerRequest.getUsername());
            return ResponseEntity.ok().build();
        }else {
            log.error("Register request failed: {}", registerRequest.getUsername());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
