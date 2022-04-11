package com.kaan.kalaha.controller;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.GameState;
import com.kaan.kalaha.enums.PitType;
import com.kaan.kalaha.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.kaan.kalaha.constant.KalahaGameConstants.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/game")
public class TestController {

    private final KalahaBoardService boardService;
    private final KalahaGameService gameService;
    private final KalahaPitService pitService;
    private final AuthService authService;

    private final KalahaPlayService kalahaPlayService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public KalahaGame createNewGame() {
        log.info("Creating new game");

        // Retrieve Logged player
        KalahaPlayer player = authService.getCurrentUser();

        // Create a new game
        KalahaGame game = gameService.createNewGame(player);

        // Create the game Board
        KalahaBoard board = boardService.createKalahaBoard(game);

        // Create Pits 6x6 layout + 2 stores
        pitService.createPit(board, PitType.HOUSE, P1_STORE, 0); // store pos 7
        pitService.createPit(board, PitType.HOUSE, P2_STORE, 0); // store post 14

        // P1 houses
        for (int i = P1_LOWER_BOUNDARY; i <= P1_UPPER_BOUNDARY; i++) {
            pitService.createPit(board, PitType.PIT, i, 6);
        }

        // P2 houses
        for (int i = P2_LOWER_BOUNDARY; i <= P2_UPPER_BOUNDARY; i++) {
            pitService.createPit(board, PitType.PIT, i, 6);
        }
        game.setKalahaBoard(board);
        gameService.update(game);

        return game;
    }

    @RequestMapping(value = "/move/{position}", method = RequestMethod.POST)
    public KalahaBoard doMove(@PathVariable int position) {
        log.info("Starting move for Player");

        // Get info
        KalahaPlayer player = authService.getCurrentUser();

        KalahaGame game = gameService.getGameById(2L);

        // Do move
        KalahaBoard board = kalahaPlayService.move(game.getId(), player.getId(), position);


        return board;
    }
}
