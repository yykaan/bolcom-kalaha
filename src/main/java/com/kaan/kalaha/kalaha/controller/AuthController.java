package com.kaan.kalaha.kalaha.controller;

import com.kaan.kalaha.dto.LoginRequest;
import com.kaan.kalaha.dto.RegisterRequest;
import com.kaan.kalaha.security.model.SecurityUser;
import com.kaan.kalaha.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Class to handle authentication requests and register requests
 * Uses {@link AuthService} to handle authentication and register requests
 * @author Kaan Yılmaz
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(description = "Authentication and registration",
        name = "Authentication")
public class AuthController {
    private final AuthService authService;

    /**
     * logs in a user with given {@link LoginRequest}
     * deletes the token from cache if it exists, so that the user can't use the same token
     * creates opaque token and saves it in cache so that actual JWT token is not exposed
     *
     * @param loginRequest {@link LoginRequest}
     * if login is successful @return {@link ResponseEntity} as {@link Map} with as "token" as key and opaque token as value
     * otherwise returns {@link ResponseEntity} with {@link HttpStatus#BAD_REQUEST}
     */
    @Operation(summary = "Authenticate user into the system and get a JWT token",
            description = "Authenticates user and returns a JWT token",
            tags = "Authentication",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful authentication",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
            })
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        log.info("Login request received: {}", loginRequest.getUsername());
        try {
            return ResponseEntity.ok(authService.authenticateUser(loginRequest));
        }catch (Exception e){
            log.error("Error while authenticating user: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * registers a new user with given {@link RegisterRequest}
     *
     * @param registerRequest {@link RegisterRequest}
     * @return {@link ResponseEntity} with {@link HttpStatus#OK} if registration is successful,
     * otherwise returns {@link ResponseEntity} with {@link HttpStatus#BAD_REQUEST}
     */
    @Operation(summary = "Register a new user",
            description = "Registers a new user with given credentials",
            tags = "Authentication",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful registration",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
            })
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegisterRequest registerRequest) {
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
