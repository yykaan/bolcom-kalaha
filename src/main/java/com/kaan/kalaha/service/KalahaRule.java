package com.kaan.kalaha.service;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.PlayerTurn;

/**
 * Responsible for applying the rules of the game.
 */
public interface KalahaRule {

    /**
     * Evaluates the game, applies rules by calling the next rule if possible.
     * @param kalahaGame {@link KalahaGame}
     * @param player {@link KalahaPlayer}
     * @param position move position {@link Integer}
     * @param playerTurn {@link PlayerTurn}
     * @return KalahaGame {@link KalahaGame}
     */
    KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn);

    /**
     * Returns the next rule.
     * @return next rule {@link KalahaRule}
     */
    KalahaRule getNextRule();

    /**
     * Switches the player turn if necessary.
     * @param kalahaGame {@link KalahaGame}
     */
    default void switchTurn(KalahaGame kalahaGame) {
        if (kalahaGame.getPlayerTurn().equals(kalahaGame.getFirstPlayer())){
            kalahaGame.setPlayerTurn(kalahaGame.getSecondPlayer());
        }else {
            kalahaGame.setPlayerTurn(kalahaGame.getFirstPlayer());
        }
    }
}
