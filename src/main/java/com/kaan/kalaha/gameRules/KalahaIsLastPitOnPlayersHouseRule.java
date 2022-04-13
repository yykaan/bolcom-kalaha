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
 * Rule to check if the last stone is on the players house
 * calls the next rule {@link KalahaGameFinishRule}
 * uses the helper {@link KalahaGameHelper}
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaIsLastPitOnPlayersHouseRule implements KalahaRule {
    private final KalahaGameHelper kalahaGameHelper;
    private final KalahaGameFinishRule kalahaGameFinishRule;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        log.info("Evaluating rule: {}", this.getClass().getSimpleName());

        if (position == kalahaGameHelper.getPlayerStoreIndexByPlayerTurn(playerTurn)) {
            return kalahaGame;
        }
        log.info("Evaluated rule: {}", this.getClass().getSimpleName());
        getNextRule().evaluate(kalahaGame, player, position, playerTurn);
        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaGameFinishRule;
    }
}
