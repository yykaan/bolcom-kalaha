package com.kaan.kalaha.service.impl;

import com.kaan.kalaha.dto.LoginRequest;
import com.kaan.kalaha.dto.RegisterRequest;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.security.model.SecurityUser;
import com.kaan.kalaha.security.service.UserDetailsServiceImpl;
import com.kaan.kalaha.security.util.JwtUtil;
import com.kaan.kalaha.service.AuthService;
import com.kaan.kalaha.service.KalahaPlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final KalahaPlayerService kalahaPlayerService;


    @Override
    public boolean isPasswordTrue(LoginRequest loginRequest) {
        KalahaPlayer kalahaPlayer = kalahaPlayerService.findPlayerByUsername(loginRequest.getUsername());
        return passwordEncoder.matches(loginRequest.getPassword(), kalahaPlayer.getPassword());
    }

    @Override
    public String generateJwtToken(SecurityUser securityUser) {
        return JwtUtil.generateToken(securityUser);
    }

    @Override
    public SecurityUser loadUserByUserName(String username) {
        return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public SecurityUser register(RegisterRequest registerRequest) {
        log.info("Registering user: {}", registerRequest.getUsername());
        if (kalahaPlayerService.findPlayerByUsername(registerRequest.getUsername()) == null) {
            log.info("Registering new user: {}", registerRequest.getUsername());
            KalahaPlayer kalahaPlayer = KalahaPlayer.builder()
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .username(registerRequest.getUsername())
                    .build();
            log.info("Saving new user: {}", kalahaPlayer);
            kalahaPlayerService.save(kalahaPlayer);
            log.info("User saved: {}", kalahaPlayer);
            return this.loadUserByUserName(registerRequest.getUsername());
        }
        log.info("User already exists: {}", registerRequest.getUsername());
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public KalahaPlayer getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User principal){
            return kalahaPlayerService.findPlayerByUsername(principal.getUsername());
        }else{
            SecurityUser principal = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return kalahaPlayerService.findPlayerByUsername(principal.getUsername());
        }

    }
}
