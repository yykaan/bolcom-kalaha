package com.kaan.kalaha.service.impl.rules;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.GameState;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.KalahaRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaIaGameFinishedRule implements KalahaRule {
    private final KalahaPlayerTurnRule playerTurnRule;
    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        if (isGameFinished(kalahaGame)){
            getNextRule().evaluate(kalahaGame, player, position,playerTurn);
        }
        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return playerTurnRule;
    }

    private boolean isGameFinished(KalahaGame kalahaGame){
        return kalahaGame.getGameState() != GameState.FINISHED;
    }
}
