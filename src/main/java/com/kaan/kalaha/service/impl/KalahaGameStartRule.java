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
public class KalahaGameStartRule implements KalahaRule {
    private final KalahaStartingPitStoneCheck kalahaStartingPitStoneCheck;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        return getNextRule().evaluate(kalahaGame, player, position, playerTurn);
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaStartingPitStoneCheck;
    }
}
