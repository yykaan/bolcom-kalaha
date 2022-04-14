package com.kaan.kalaha.kalaha.gameRules;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.gameRules.KalahaIsPlayerInTurnRule;
import com.kaan.kalaha.service.KalahaRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * First rule of the game
 * Evaluates if the game can start
 * calls the next rule {@link KalahaIsPlayerInTurnRule}
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaGameStartRule implements KalahaRule {
    private final KalahaIsPlayerInTurnRule kalahaIsPlayerInTurnRule;
    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        log.info("Game start rule is being evaluated");
        log.info("Move position is {}", position);
        log.info("Player is {}", player);
        log.info("Game is {}", kalahaGame);
        return getNextRule().evaluate(kalahaGame, player, position, playerTurn);
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaIsPlayerInTurnRule;
    }
}
