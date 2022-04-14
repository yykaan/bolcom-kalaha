package com.kaan.kalaha;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaan.kalaha.controller.GameController;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.security.filter.JwtFilter;
import com.kaan.kalaha.service.*;
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
import java.util.ArrayList;
import java.util.Collections;

import static com.kaan.kalaha.TestUtils.createKalahaPlayer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = GameController.class)
public class GameControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    JwtFilter jwtFilter;

    @MockBean
    KalahaBoardService boardService;

    @MockBean
    KalahaGameService gameService;

    @MockBean
    KalahaPitService pitService;

    @MockBean
    AuthService authService;

    @MockBean
    KalahaPlayService kalahaPlayService;

    @Test
    public void gameGameById_success() throws Exception {
        KalahaGame kalahaGame = new KalahaGame();
        kalahaGame.setId(1L);
        Mockito.when(gameService.getGameById(1L))
                .thenReturn(kalahaGame);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/game/"+1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset()))
                .andReturn();

        KalahaGame actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), KalahaGame.class);

        long expected = 1L;

        assertThat(actual.getId()).isEqualTo(expected);

    }

    @Test
    public void gameGameById_failByQueryWithWrongId() throws Exception {
        KalahaGame kalahaGame1 = new KalahaGame();
        kalahaGame1.setId(1L);
        Mockito.when(gameService.getGameById(1L))
                .thenReturn(kalahaGame1);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/game/"+5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset()))
                .andReturn();


        assertThat(mvcResult.getResponse().getContentAsString()).isEmpty();
    }

    @Test
    public void gameGameById_failWithPost_methodNotAllowedError() throws Exception {
        Mockito.when(gameService.getGameById(anyLong()))
                .thenReturn(new KalahaGame());

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/game/"+1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(objectMapper.writeValueAsString(new KalahaGame())))
                .andReturn();

        int actual = mvcResult.getResponse().getStatus();

        int expected = HttpStatus.METHOD_NOT_ALLOWED.value();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void getGamesToJoin_success() throws Exception {
        Mockito.when(gameService.getGamesToJoin(any(KalahaPlayer.class)))
                .thenReturn(new ArrayList<>());

        Mockito.when(authService.getCurrentUser())
                .thenReturn(createKalahaPlayer());

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/game/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset()))
                .andReturn();

        int actual = mvcResult.getResponse().getStatus();

        int expected = HttpStatus.OK.value();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void joinGame_success() throws Exception {
        Mockito.when(gameService.getGamesToJoin(any(KalahaPlayer.class)))
                .thenReturn(new ArrayList<>());

        Mockito.when(gameService.joinGame(any(KalahaPlayer.class), anyLong()))
                .thenReturn(new KalahaGame());

        Mockito.when(authService.getCurrentUser())
                .thenReturn(createKalahaPlayer());

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/game/join/"+1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset()))
                .andReturn();

        int actual = mvcResult.getResponse().getStatus();

        int expected = HttpStatus.OK.value();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void joinGame_failQueryWithWrongId() throws Exception {
        KalahaGame kalahaGameToJoin = new KalahaGame();
        kalahaGameToJoin.setId(5L);
        Mockito.when(gameService.getGamesToJoin(any(KalahaPlayer.class)))
                .thenReturn(Collections.singletonList(kalahaGameToJoin));

        Mockito.when(gameService.joinGame(any(KalahaPlayer.class), anyLong()))
                .thenReturn(new KalahaGame());

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/game/join/"+1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset()))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEmpty();

    }

    @Test
    public void doMove_success() throws Exception {
        Mockito.when(kalahaPlayService.move(anyLong(), anyInt()))
                .thenReturn(new KalahaGame());

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/game/"+anyInt()+"/move/"+anyInt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset()))
                .andReturn();

        int actual = mvcResult.getResponse().getStatus();

        int expected = HttpStatus.OK.value();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void doMove_fail() throws Exception {
        KalahaGame kalahaGame = new KalahaGame();
        kalahaGame.setId(1L);
        Mockito.when(kalahaPlayService.move(1L, 1))
                .thenReturn(kalahaGame);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/game/"+5L+"/move/"+1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset()))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEmpty();

    }

    @Test
    public void createGame_success() throws Exception {
        KalahaGame game = new KalahaGame();
        game.setId(1L);
        Mockito.when(gameService.createNewGame())
                .thenReturn(game);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/game/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset()))
                .andReturn();

        KalahaGame actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), KalahaGame.class);

        assertThat(actual.getId()).isEqualTo(game.getId());
    }
}
