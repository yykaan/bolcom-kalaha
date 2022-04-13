package com.kaan.kalaha.controller;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class that handles the game logic
 * uses {@link KalahaBoardService} to get the board related business logic
 * uses {@link KalahaGameService} to get the game related business logic
 * uses {@link KalahaPitService} to get the pit related business logic
 * uses {@link AuthService} to get current logged-in user
 *
 * uses {@link KalahaPlayService} to evaluate the play by rules
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/game")
public class GameController {

    private final KalahaBoardService boardService;
    private final KalahaGameService gameService;
    private final KalahaPitService pitService;
    private final AuthService authService;

    private final KalahaPlayService kalahaPlayService;

    /**
     * Returns the game with given id
     *
     * @param gameId game id
     * @return KalahaGame {@link KalahaGame}
     */
    @GetMapping("/{gameId}")
    public KalahaGame getGameById(@PathVariable Long gameId){
        return gameService.getGameById(gameId);
    }

    /**
     * creates KalahaBoard {@link KalahaBoard} for KalahaGame {@link KalahaGame},
     * create KalahaPits {@link KalahaPit} for KalahaBoard {@link KalahaBoard}
     *
     * @return KalahaGame {@link KalahaGame} with logged-in user as player {@link KalahaPlayer}
     */
    @PostMapping(value = "/create")
    public KalahaGame createNewGame() {
        log.info("Creating new game");

        // Create a new game
        KalahaGame game = gameService.createNewGame();

        // Create the game Board
        KalahaBoard board = boardService.createKalahaBoard(game);
        pitService.createPits(board);

        game.setKalahaBoard(board);

        gameService.update(game);

        return game;
    }

    /**
     * Makes a move for the current logged-in user
     * evaluates the play by rules {@link KalahaRule}
     *
     * @param gameId game id {@link KalahaGame}
     * @param position pit position {@link KalahaPit.position}
     * @return KalahaGame {@link KalahaGame}
     */
    @PostMapping(value = "{gameId}/move/{position}")
    public KalahaGame doMove(@PathVariable long gameId, @PathVariable int position) {
        log.info("Starting move for Player");
        return kalahaPlayService.move(gameId, position);
    }

    /**
     * Allow logged-in user {@link KalahaPlayer} to join the game
     * @param id game id
     * @return KalahaGame {@link KalahaGame}
     */
    @PostMapping(value = "/join/{id}")
    public KalahaGame joinGame(@PathVariable Long id) {
        log.info("Joining game");
        KalahaPlayer player = authService.getCurrentUser();

        return gameService.joinGame(player, id);
    }

    /**
     * Returns the list of games for logged-in user
     * @return list of KalahaGame {@link KalahaGame}
     */
    @GetMapping(value = "/list")
    public List<KalahaGame> getGamesToJoin() {
        log.info("Getting games to Join");
        return gameService.getGamesToJoin(authService.getCurrentUser());
    }
}
