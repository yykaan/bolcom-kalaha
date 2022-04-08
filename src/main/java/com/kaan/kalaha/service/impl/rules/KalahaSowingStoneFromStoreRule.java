package com.kaan.kalaha.service.impl.rules;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.KalahaRule;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaSowingStoneFromStoreRule implements KalahaRule {
    private final KalahaGameHelper kalahaGameHelper;
    private final KalahaIsPitHasStoneRule kalahaIsPitHasStoneRule;

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
        return kalahaIsPitHasStoneRule;
    }
}