package com.kaan.kalaha.gameRules;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.KalahaRule;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Rule to validate starting position and stone count by turn
 * calls the next rule {@link KalahaValidateStartingPositionAndStoneCountByTurnRule}
 * uses {@link KalahaGameHelper} to validate the starting position and stone count
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaValidateStartingPositionAndStoneCountByTurnRule implements KalahaRule {

    private final KalahaGameHelper kalahaGameHelper;
    private final KalahaIsStartingPitStoreRule kalahaIsStartingPitStoreRule;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        log.info("Validating starting position and stone count by turn");
        int stones = kalahaGame.getKalahaBoard().getPits().stream()
                .filter(kalahaGameHelper.getGetPitByPosition(position))
                .findFirst()
                .get()
                .getStones();

        if (kalahaGameHelper.validateStartingPitPositionByPlayerTurnAndPitHasStone(playerTurn, position, stones)) {
            log.info("Starting pit position and stone count on starting pit are valid");
            getNextRule().evaluate(kalahaGame, player, position, playerTurn);
        }
        log.info("Starting pit position or stone count on starting pit are not valid");
        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaIsStartingPitStoreRule;
    }
}
