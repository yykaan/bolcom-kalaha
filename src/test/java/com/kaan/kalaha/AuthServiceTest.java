package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.security.model.SecurityUser;
import com.kaan.kalaha.security.service.UserDetailsServiceImpl;
import com.kaan.kalaha.security.util.JwtUtil;
import com.kaan.kalaha.service.KalahaPlayerService;
import com.kaan.kalaha.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.kaan.kalaha.TestUtils.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserDetailsServiceImpl userDetailsService;

    @Mock
    KalahaPlayerService kalahaPlayerService;

    @InjectMocks
    AuthServiceImpl authService;

    @Test
    public void isPasswordTrueTest(){
        when(kalahaPlayerService.findPlayerByUsername(anyString()))
                .thenReturn(new KalahaPlayer("username","password","a@bol.com"));

        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(true);

        boolean isPasswordTrue = authService.isPasswordTrue(createLoginRequest());

        assertThat(isPasswordTrue).isTrue();
    }

    @Test
    public void isPasswordTrueTest_failPasswordsDoNotMatch(){
        when(kalahaPlayerService.findPlayerByUsername(anyString()))
                .thenReturn(new KalahaPlayer("username","password","a@bol.com"));

        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(false);

        boolean isPasswordTrue = authService.isPasswordTrue(createLoginRequest());

        assertThat(isPasswordTrue).isFalse();
    }

    @Test
    public void generateJwtToken(){
        try (MockedStatic<JwtUtil> utilities = Mockito.mockStatic(JwtUtil.class)) {
            utilities.when(() -> JwtUtil.generateToken(any(SecurityUser.class)))
                    .thenReturn("token");

            assertThat(JwtUtil.generateToken(createSecurityUser())).isEqualTo("token");
            String token = authService.generateJwtToken(createSecurityUser());
            assertThat(token).isEqualTo("token");
        }
    }

    @Test
    public void loadUserByUsername(){
        when(userDetailsService.loadUserByUsername(anyString()))
                .thenReturn(createSecurityUser());

        SecurityUser securityUser = userDetailsService.loadUserByUsername(createSecurityUser().getUsername());
        assertThat(securityUser.getUsername()).isEqualTo(createSecurityUser().getUsername());
    }

    @Test
    public void register_success(){
        when(kalahaPlayerService.findPlayerByUsername(anyString()))
                .thenReturn(null);

        when(passwordEncoder.encode(createRegisterRequest().getPassword()))
                .thenReturn("encodedPassword");

        when(userDetailsService.loadUserByUsername(anyString()))
                .thenReturn(createSecurityUser());

        SecurityUser securityUser = authService.register(createRegisterRequest());
        assertThat(securityUser.getUsername()).isEqualTo(createSecurityUser().getUsername());
    }

    @Test
    public void register_failUserAlreadyExists(){
        when(kalahaPlayerService.findPlayerByUsername(anyString()))
                .thenReturn(createKalahaPlayer());

        SecurityUser securityUser = authService.register(createRegisterRequest());
        assertThat(securityUser).isNull();
    }

    @Test
    public void getCurrentUser(){
        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(createSecurityUser());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        when(kalahaPlayerService.findPlayerByUsername(anyString()))
                .thenReturn(createKalahaPlayer());

        KalahaPlayer currentUser = authService.getCurrentUser();
        assertThat(currentUser.getUsername()).isEqualTo(createKalahaPlayer().getUsername());
    }

    @Test
    public void getCurrentUser_failNotLoggedIn(){
        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(createSecurityUser());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        when(kalahaPlayerService.findPlayerByUsername(anyString()))
                .thenReturn(null);

        KalahaPlayer currentUser = authService.getCurrentUser();
        assertThat(currentUser).isNull();
    }
}
