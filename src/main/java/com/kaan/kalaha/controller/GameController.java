package com.kaan.kalaha.controller;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.PitType;
import com.kaan.kalaha.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kaan.kalaha.constant.KalahaGameConstants.*;

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

    @GetMapping("/{gameId}")
    public KalahaGame getGameById(@PathVariable Long gameId){
        return gameService.getGameById(gameId);
    }

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

    @PostMapping(value = "{gameId}/move/{position}")
    public KalahaGame doMove(@PathVariable long gameId, @PathVariable int position) {
        log.info("Starting move for Player");
        return kalahaPlayService.move(gameId, position);
    }

    @PostMapping(value = "/join/{id}")
    public KalahaGame joinGame(@PathVariable Long id) {
        log.info("Joining game");
        KalahaPlayer player = authService.getCurrentUser();

        return gameService.joinGame(player, id);
    }

    @GetMapping(value = "/list")
    public List<KalahaGame> getGamesToJoin() {
        log.info("Getting games to Join");

        return gameService.getGamesToJoin(authService.getCurrentUser());
    }
}
