package com.kaan.kalaha;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaan.kalaha.config.cache.CacheManager;
import com.kaan.kalaha.controller.AuthController;
import com.kaan.kalaha.dto.LoginRequest;
import com.kaan.kalaha.dto.RegisterRequest;
import com.kaan.kalaha.security.filter.JwtFilter;
import com.kaan.kalaha.security.model.SecurityUser;
import com.kaan.kalaha.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static com.kaan.kalaha.TestUtils.createLoginRequest;
import static com.kaan.kalaha.TestUtils.createRegisterRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = AuthController.class)
public class AuthControllerTest {

    @MockBean
    CacheManager cacheManager;

    @MockBean
    AuthService authService;

    @MockBean
    JwtFilter jwtFilter;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void register_success() throws Exception {
        RegisterRequest registerRequest = createRegisterRequest();

        SecurityUser securityUser = new SecurityUser("bolcomtest");

        Mockito.when(authService.register(any(RegisterRequest.class)))
                .thenReturn(securityUser);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andReturn();

        int actual = mvcResult.getResponse().getStatus();

        int expected = HttpStatus.OK.value();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void register_failWithEmptyRequest() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();

        Mockito.when(authService.register(any(RegisterRequest.class)))
                .thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andReturn();

        int actual = mvcResult.getResponse().getStatus();

        int expected = HttpStatus.BAD_REQUEST.value();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void login_success() throws Exception {
        RegisterRequest registerRequest = createRegisterRequest();

        SecurityUser securityUser = new SecurityUser("bolcomtest");

        Mockito.when(authService.register(any(RegisterRequest.class)))
                .thenReturn(securityUser);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andReturn();

        int actual = mvcResult.getResponse().getStatus();

        int expected = HttpStatus.OK.value();

        assertThat(actual).isEqualTo(expected);

        LoginRequest loginRequest = createLoginRequest();

        Map<String, Object> responseMap = new HashMap<>();

        Mockito.when(authService.authenticateUser(any(LoginRequest.class)))
                .thenReturn(responseMap);

        MvcResult mvcResultLogin = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn();

        int actualLogin = mvcResultLogin.getResponse().getStatus();

        int expectedLogin = HttpStatus.OK.value();

        assertThat(actualLogin).isEqualTo(expectedLogin);
        assertThat(mvcResultLogin.getResponse().getContentAsString()).isNotEmpty();
    }

    @Test
    public void login_fail() throws Exception {
        RegisterRequest registerRequest = createRegisterRequest();

        SecurityUser securityUser = new SecurityUser("bolcomtest");

        Mockito.when(authService.register(any(RegisterRequest.class)))
                .thenReturn(securityUser);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andReturn();

        int actual = mvcResult.getResponse().getStatus();

        int expected = HttpStatus.OK.value();

        assertThat(actual).isEqualTo(expected);

        MvcResult mvcResultLoginResponse = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset()))
                .andReturn();

        int actualLoginResponse = mvcResultLoginResponse.getResponse().getStatus();

        int expectedLoginResposne = HttpStatus.BAD_REQUEST.value();

        assertThat(actualLoginResponse).isEqualTo(expectedLoginResposne);
    }

}
