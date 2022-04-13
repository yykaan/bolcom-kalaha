package com.kaan.kalaha.service.impl.rules;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.GameState;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.KalahaRule;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
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
        log.info("Post Finish Game Rule");
        Arrays.stream(PlayerTurn.values()).forEach(turn -> {
            emptyPlayerPitsByPlayerTurnAndPutStonesOnPlayerHouse(kalahaGame.getKalahaBoard(), turn);
        });
        kalahaGame.setGameState(GameState.FINISHED);

        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return null;
    }

    private void emptyPlayerPitsByPlayerTurnAndPutStonesOnPlayerHouse(KalahaBoard kalahaBoard, PlayerTurn playerTurn) {
        log.info("Empty Player Pits By Player Turn And Put Stones On Player House");
        KalahaPit playerHouse = kalahaBoard.getPits().stream()
                .filter(kalahaGameHelper.getGetPlayerHouse()).toList().get(0);

        kalahaBoard.getPits().stream()
                .filter(kalahaGameHelper.getPlayerPits(playerTurn))
                .filter(kalahaGameHelper.getGetPlayerOnlyPits())
                .forEach(kalahaPit -> {
                    int totalStone = 0;
                    totalStone = totalStone + kalahaPit.getStones();
                    kalahaPit.setStones(0);
                    playerHouse.setStones(playerHouse.getStones() + totalStone);
                });
    }
}
