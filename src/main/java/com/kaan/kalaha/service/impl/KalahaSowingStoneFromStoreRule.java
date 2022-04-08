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
public class KalahaSowingStoneFromStoreRule implements KalahaRule {
    private final KalahaGameHelper kalahaGameHelper;
    private final KalahaIsPitHasStoneRule kalahaIsPitHasStoneRule;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        if (!kalahaGameHelper.isStartingPitStore(position)) {
            getNextRule().evaluate(kalahaGame, player, position, playerTurn);
        }
        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaIsPitHasStoneRule;
    }
}