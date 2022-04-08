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
public class KalahaValidateStartingPositionAndStoneCountByTurnRule implements KalahaRule {

    private final KalahaGameHelper kalahaGameHelper;
    private final KalahaSowingStoneFromStoreRule kalahaSowingStoneFromStoreRule;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {

        int stones = kalahaGame.getKalahaBoard().getPits().stream()
                .filter(kalahaGameHelper.getGetPitByPosition(position))
                .findFirst()
                .get()
                .getStones();

        if (kalahaGameHelper.validateStartingPitPositionByPlayerTurn(playerTurn, position, stones)) {
            getNextRule().evaluate(kalahaGame, player, position, playerTurn);
        }

        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaSowingStoneFromStoreRule;
    }
}
