package com.kaan.kalaha;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaan.kalaha.dto.LoginRequest;
import com.kaan.kalaha.dto.RegisterRequest;
import com.kaan.kalaha.security.model.SecurityUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { KalahaApplication.class })
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application.properties")
public class AuthControllerIntegrationTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesAuthController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("authController"));
    }

    @Test
    public void register_success() throws Exception {
        RegisterRequest registerRequest = createRegisterRequest();

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
    public void register_failWithSameEmail() throws Exception {
        RegisterRequest registerRequest = createRegisterRequest();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andReturn();

        int actual = mvcResult.getResponse().getStatus();

        int expected = HttpStatus.OK.value();

        assertThat(actual).isEqualTo(expected);

        mvcResult = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andReturn();

        actual = mvcResult.getResponse().getStatus();

        expected = HttpStatus.BAD_REQUEST.value();

        assertThat(actual).isEqualTo(expected);
    }

    private RegisterRequest createRegisterRequest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("a@bol.com");
        registerRequest.setUsername("bolcomtest");
        registerRequest.setPassword("strongpassword");
        return registerRequest;
    }

    private LoginRequest createLoginRequest(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("bolcomtest");
        loginRequest.setPassword("strongpassword");
        return loginRequest;
    }
}
