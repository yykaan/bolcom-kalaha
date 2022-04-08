package com.kaan.kalaha.service.impl;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.KalahaRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaPostFinishGameRule implements KalahaRule {
    private final KalahaGameHelper kalahaGameHelper;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        Arrays.stream(PlayerTurn.values()).forEach(turn -> {
            emptyPlayerPitsByPlayerTurnAndPutStonesOnPlayerHouse(kalahaGame.getKalahaBoard(), turn);
        });

        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return null;
    }

    private void emptyPlayerPitsByPlayerTurnAndPutStonesOnPlayerHouse(KalahaBoard kalahaBoard, PlayerTurn playerTurn) {
        KalahaPit playerHouse = kalahaBoard.getPits().stream()
                .filter(kalahaGameHelper.getGetPlayerPits(playerTurn))
                .filter(kalahaGameHelper.getGetPlayerHouse()).toList().get(0);

        kalahaBoard.getPits().stream()
                .filter(kalahaGameHelper.getGetPlayerPits(playerTurn))
                .filter(kalahaGameHelper.getGetPlayerOnlyPits())
                .forEach(kalahaPit -> {
                    int totalStone = 0;
                    totalStone = totalStone + kalahaPit.getStones();
                    kalahaPit.setStones(0);
                    playerHouse.setStones(playerHouse.getStones() + totalStone);
                });
    }
}
