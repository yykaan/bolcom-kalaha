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
public class KalahaGameStartRule implements KalahaRule {
    private final KalahaStartingPitStoneCheckRule kalahaStartingPitStoneCheckRule;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        log.info("Game start rule is evaluated");
        log.info("Player turn is {}", playerTurn);
        log.info("Player position is {}", position);
        log.info("Player is {}", player);
        log.info("Game is {}", kalahaGame);
        return getNextRule().evaluate(kalahaGame, player, position, playerTurn);
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaStartingPitStoneCheckRule;
    }
}
