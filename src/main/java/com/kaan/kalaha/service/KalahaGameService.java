package com.kaan.kalaha.service;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.GameState;

import java.util.List;

/**
 * Responsible {@link KalahaGame} related operations.
 */
public interface KalahaGameService {
    /**
     * Creates new game with currenly logged-in user {@link KalahaPlayer} as first player.
     * @return newly created {@link KalahaGame}
     */
    KalahaGame createNewGame();

    /**
     * Update game {@link KalahaGame}
     * @param kalahaGame {@link KalahaGame}
     * @return updated {@link KalahaGame}
     */
    KalahaGame update(KalahaGame kalahaGame);

    /**
     * Allow player to join given game with ID.
     * @param player {@link KalahaPlayer}
     * @param gameId {@link KalahaGame#id}
     * @return
     */
    KalahaGame joinGame(KalahaPlayer player, Long gameId);

    /**
     * Update game state
     * @param game {@link KalahaGame}
     * @param gameState {@link GameState}
     * @return updated {@link KalahaGame}
     */
    KalahaGame updateGameState(KalahaGame game, GameState gameState);

    /**
     * Returns game with given ID
     * @param id {@link KalahaGame#id}
     * @return {@link KalahaGame}
     */
    KalahaGame getGameById(Long id);

    /**
     * Returns all available games to join
     * @param player {@link KalahaPlayer}
     * @return {@link List<KalahaGame>}
     */
    List<KalahaGame> getGamesToJoin(KalahaPlayer player);
}
