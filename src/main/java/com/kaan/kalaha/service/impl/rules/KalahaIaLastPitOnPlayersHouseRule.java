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
public class KalahaIaLastPitOnPlayersHouseRule implements KalahaRule {
    private final KalahaGameHelper kalahaGameHelper;
    private final KalahaGameFinishRule kalahaGameFinishRule;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        log.info("evaluate: {}", kalahaGame);

        if (position != kalahaGameHelper.getPlayerStoreIndexByPlayerTurn(playerTurn)) {
            if (PlayerTurn.P1.equals(playerTurn)) {
                kalahaGame.setPlayerTurn(kalahaGame.getSecondPlayer());
                playerTurn = PlayerTurn.P2;
            }else {
                kalahaGame.setPlayerTurn(kalahaGame.getFirstPlayer());
                playerTurn = PlayerTurn.P1;
            }
        }
        log.info("evaluate: {}", kalahaGame);
        getNextRule().evaluate(kalahaGame, player, position, playerTurn);
        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaGameFinishRule;
    }
}
