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
 * Rule to check if the player is starting from the pit store or house
 * calls the next rule {@link KalahaStoneSowingRule}
 * uses the helper class {@link KalahaGameHelper}
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaIsStartingPitStoreRule implements KalahaRule {
    private final KalahaGameHelper kalahaGameHelper;
    private final KalahaStoneSowingRule kalahaStoneSowingRule;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        log.info("Sowing stone from store rule");
        if (!kalahaGameHelper.isStartingPitStore(position)) {
            log.info("Sowing stone from store rule is not starting pit store");
            getNextRule().evaluate(kalahaGame, player, position, playerTurn);
        }
        log.info("Sowing stone from store rule is starting pit store");
        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaStoneSowingRule;
    }
}
