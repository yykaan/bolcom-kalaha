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
public class KalahaGameFinishRule implements KalahaRule {
    private final KalahaGameHelper kalahaGameHelper;
    private final KalahaPostFinishGameRule kalahaPostFinishGameRule;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        if (kalahaGame.getKalahaBoard().getPits().stream()
                .filter(kalahaGameHelper.getGetPlayerPits(playerTurn))
                .filter(kalahaGameHelper.getGetPlayerOnlyPits())
                .noneMatch(kalahaGameHelper.getIsPitHasStone())){
            getNextRule().evaluate(kalahaGame, player, position, playerTurn);
        }
        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaPostFinishGameRule;
    }
}
