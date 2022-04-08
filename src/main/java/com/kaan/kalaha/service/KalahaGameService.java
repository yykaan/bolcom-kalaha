package com.kaan.kalaha.service;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.GameState;

import java.util.List;

public interface KalahaGameService {
    KalahaGame createNewGame(KalahaPlayer player);

    KalahaGame joinGame(KalahaPlayer player, Long gameId);

    KalahaGame switchTurn(KalahaPlayer player, Long gameId);

    KalahaGame updateGameState(KalahaGame game, GameState gameState);

    KalahaGame getGameById(Long id);

    List<KalahaGame> getGamesToJoin(KalahaPlayer player);

    List<KalahaGame> getPlayerGames(KalahaPlayer player);
}
