package com.kaan.kalaha.service.impl.rules;

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
public class KalahaPlayerTurnRule implements KalahaRule {
    private final KalahaValidateStartingPositionAndStoneCountByTurnRule kalahaValidateStartingPositionAndStoneCountByTurnRule;
    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        playerTurn = determinePlayerTurn(kalahaGame, player);
        getNextRule().evaluate(kalahaGame, player, position, playerTurn);
        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaValidateStartingPositionAndStoneCountByTurnRule;
    }

    private PlayerTurn determinePlayerTurn(KalahaGame kalahaGame, KalahaPlayer kalahaPlayer) {
        PlayerTurn playerTurn;
        if(kalahaPlayer == kalahaGame.getFirstPlayer()) {
            log.info("Player {} is P1 for game {}", kalahaPlayer, kalahaGame);
            playerTurn = PlayerTurn.P1;
            kalahaGame.setPlayerTurn(kalahaGame.getFirstPlayer());
        }
        else {
            log.info("Player {} is P2 for game {}", kalahaPlayer, kalahaGame);
            playerTurn = PlayerTurn.P2;
            kalahaGame.setPlayerTurn(kalahaGame.getSecondPlayer());
        }
        return playerTurn;
    }
}
