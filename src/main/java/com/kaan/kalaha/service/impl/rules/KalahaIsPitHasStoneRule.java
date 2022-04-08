package com.kaan.kalaha.service.impl.rules;

import com.google.common.collect.Iterables;
import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.KalahaRule;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaIsPitHasStoneRule implements KalahaRule {
    private final KalahaGameHelper kalahaGameHelper;
    private final KalahaStoneSowingRule kalahaStoneSowingRule;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        KalahaBoard kalahaBoard = kalahaGame.getKalahaBoard();
        KalahaPit startingKalahaPit = Iterables.getOnlyElement(kalahaBoard.getPits().stream()
                .filter(kalahaGameHelper.getGetPitByPosition(position))
                .collect(Collectors.toList()));

        int stones = startingKalahaPit.getStones();

        if (stones > 0) {
            getNextRule().evaluate(kalahaGame, player, position, playerTurn);
        }
        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaStoneSowingRule;
    }
}
