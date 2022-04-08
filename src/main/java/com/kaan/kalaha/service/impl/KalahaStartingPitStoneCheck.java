package com.kaan.kalaha.service.impl;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.KalahaRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaStartingPitStoneCheck implements KalahaRule {

    private final KalahaIsGameFinished kalahaIsGameFinished;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        if (isPlayerTurn(kalahaGame, player)) {
            getNextRule().evaluate(kalahaGame, player, position, playerTurn);
        }
        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaIsGameFinished;
    }

    private boolean isPlayerTurn(KalahaGame kalahaGame, KalahaPlayer kalahaPlayer) {
        log.info("Checking if player turn {}", kalahaPlayer);
        boolean isPlayerTurn =  kalahaGame.getPlayerTurn().equals(kalahaPlayer);
        log.info("Player {} is in turn {}", kalahaPlayer, isPlayerTurn);
        return isPlayerTurn;
    }
}
