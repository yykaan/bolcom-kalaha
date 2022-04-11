package com.kaan.kalaha.service;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.PlayerTurn;

public interface KalahaRule {

    KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn);
    KalahaRule getNextRule();

    default void switchTurn(KalahaGame kalahaGame) {
        if (kalahaGame.getPlayerTurn().equals(kalahaGame.getFirstPlayer())){
            kalahaGame.setPlayerTurn(kalahaGame.getSecondPlayer());
        }else {
            kalahaGame.setPlayerTurn(kalahaGame.getFirstPlayer());
        }
    }
}
