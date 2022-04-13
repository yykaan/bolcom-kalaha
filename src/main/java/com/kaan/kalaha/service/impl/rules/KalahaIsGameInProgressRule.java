package com.kaan.kalaha.service.impl.rules;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.GameState;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.KalahaRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Rule to check if game is in progress
 * calls the next rule {@link KalahaValidateStartingPositionAndStoneCountByTurnRule}
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaIsGameInProgressRule implements KalahaRule {
    private final KalahaValidateStartingPositionAndStoneCountByTurnRule kalahaValidateStartingPositionAndStoneCountByTurnRule;
    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        log.info("Game is in progress rule is evaluating");
        if (isGameStillInProgress(kalahaGame)){
            log.info("Game is in progress rule evaluated - Game in progress, evaluate next rule");
            getNextRule().evaluate(kalahaGame, player, position,playerTurn);
        }
        log.info("Game is in progress rule - Game is {}!", kalahaGame.getGameState());
        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaValidateStartingPositionAndStoneCountByTurnRule;
    }

    private boolean isGameStillInProgress(KalahaGame kalahaGame){
        return kalahaGame.getGameState() == GameState.IN_PROGRESS;
    }
}
