package com.kaan.kalaha.service;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.GameState;

import java.util.List;

public interface KalahaGameService {
    KalahaGame createNewGame();

    KalahaGame update(KalahaGame kalahaGame);

    KalahaGame joinGame(KalahaPlayer player, Long gameId);

    KalahaGame updateGameState(KalahaGame game, GameState gameState);

    KalahaGame getGameById(Long id);

    List<KalahaGame> getGamesToJoin(KalahaPlayer player);
}
