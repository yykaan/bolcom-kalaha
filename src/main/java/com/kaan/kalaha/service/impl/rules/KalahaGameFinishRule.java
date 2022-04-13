package com.kaan.kalaha.service.impl.rules;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.KalahaRule;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Rule to check if the game is finished.
 * calls the next rule {@link KalahaPostFinishGameRule}
 * uses the helper {@link KalahaGameHelper}
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaGameFinishRule implements KalahaRule {
    private final KalahaGameHelper kalahaGameHelper;
    private final KalahaPostFinishGameRule kalahaPostFinishGameRule;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        log.info("Game finish rule is evaluating");
        if (kalahaGame.getKalahaBoard().getPits().stream()
                .filter(kalahaGameHelper.getPlayerPits(PlayerTurn.P1))
                .filter(kalahaGameHelper.getGetPlayerOnlyPits())
                .noneMatch(kalahaGameHelper.getIsPitHasStone()) ||

                kalahaGame.getKalahaBoard().getPits().stream()
                        .filter(kalahaGameHelper.getPlayerPits(PlayerTurn.P2))
                        .filter(kalahaGameHelper.getGetPlayerOnlyPits())
                        .noneMatch(kalahaGameHelper.getIsPitHasStone())){
            getNextRule().evaluate(kalahaGame, player, position, playerTurn);
        }
        switchTurn(kalahaGame);
        log.info("Game finish rule is evaluated");
        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaPostFinishGameRule;
    }
}
