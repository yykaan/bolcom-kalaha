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
public class KalahaIsLastPitOnPlayersHouse implements KalahaRule {
    private final KalahaGameHelper kalahaGameHelper;
    private final KalahaGameFinishRule kalahaGameFinishRule;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {

        if (position != kalahaGameHelper.getPlayerStoreIndexByPlayerTurn(playerTurn)) {
            if (PlayerTurn.P1.equals(playerTurn)) {
                kalahaGame.setPlayerTurn(kalahaGame.getSecondPlayer());
                playerTurn = PlayerTurn.P2;
            }else {
                kalahaGame.setPlayerTurn(kalahaGame.getFirstPlayer());
                playerTurn = PlayerTurn.P1;
            }
        }
        getNextRule().evaluate(kalahaGame, player, position, playerTurn);
        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaGameFinishRule;
    }
}
