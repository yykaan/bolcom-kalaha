package com.kaan.kalaha.kalaha.gameRules;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.gameRules.KalahaIsLastPitOnPlayersHouseRule;
import com.kaan.kalaha.service.KalahaRule;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Rule to check if the player can capture the opponent's stones.
 * calls the next rule {@link com.kaan.kalaha.gameRules.KalahaIsLastPitOnPlayersHouseRule}
 * uses the {@link KalahaGameHelper}
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaCaptureRule implements KalahaRule {
    private final KalahaGameHelper kalahaGameHelper;
    private final KalahaIsLastPitOnPlayersHouseRule kalahaIsLastPitOnPlayersHouseRule;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        log.info("Evaluating KalahaCaptureRule");
        KalahaBoard kalahaBoard = kalahaGame.getKalahaBoard();

        if (position >= kalahaGameHelper.getLowerPositionByPlayerTurn(playerTurn) &&
                position <= kalahaGameHelper.getUpperPositionByPlayerTurn(playerTurn) &&
                kalahaBoard.getPits().stream().filter(kalahaGameHelper.getGetPitByPosition(position)).findFirst().get().getStones() == 1) {
            log.info("Capturing stone can be evaluated");

            Integer oppositePitStones = kalahaBoard.getPits().stream()
                    .filter(kalahaGameHelper.getGetPitByPosition(kalahaGameHelper.getOppositePitIndex(position)))
                    .findFirst().get().getStones();

            if (oppositePitStones > 0) {
                log.info("Opposite pit has stones");
                kalahaBoard.getPits().stream()
                        .filter(kalahaGameHelper.getGetPitByPosition(kalahaGameHelper.getOppositePitIndex(position)))
                        .findFirst()
                        .ifPresentOrElse(kalahaPit -> {
                            kalahaPit.setStones(0);
                        },() -> {
                            log.info("Opposite pit is empty");
                        });

                kalahaBoard.getPits().stream()
                        .filter(kalahaGameHelper.getGetPitByPosition(position))
                        .findFirst()
                        .ifPresentOrElse(kalahaPit -> {
                            kalahaPit.setStones(0);
                        },() -> {
                            log.info("Pit is empty");
                        });

                kalahaBoard.getPits().stream()
                        .filter(kalahaGameHelper.getGetPlayerHouse(playerTurn))
                        .findFirst()
                        .ifPresentOrElse(kalahaPit -> {
                            kalahaPit.setStones(kalahaPit.getStones() + oppositePitStones + 1);
                        },() -> {
                            log.info("Player house is empty");
                        });
                log.info("Capturing stone is evaluated");
                log.info("Switching player");
                switchTurn(kalahaGame);
            }
        }

        log.info("KalahaCaptureRule evaluating completed");
        getNextRule().evaluate(kalahaGame, player, position, playerTurn);
        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaIsLastPitOnPlayersHouseRule;
    }
}
