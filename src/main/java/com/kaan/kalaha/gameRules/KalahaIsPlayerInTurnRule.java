package com.kaan.kalaha.gameRules;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.KalahaRule;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Rule to check if the player is in turn
 * calls the next rule {@link KalahaIsGameInProgressRule}
 * uses {@link KalahaGameHelper} to evaluate certain common conditions
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaIsPlayerInTurnRule implements KalahaRule {

    private final KalahaIsGameInProgressRule kalahaIsGameInProgressRule;
    private final KalahaGameHelper kalahaGameHelper;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        log.info("Check if it is player turn {}", player);
        if (isPlayerTurn(kalahaGame, player)) {
            log.info("KalahaPlayer is in turn {}", player);
            playerTurn = determinePlayerTurn(kalahaGame);
            getNextRule().evaluate(kalahaGame, player, position, playerTurn);
            return kalahaGame;
        }else{
            log.info("Player {} is in wrong turn!", player);
            return kalahaGame;
        }
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaIsGameInProgressRule;
    }

    private boolean isPlayerTurn(KalahaGame kalahaGame, KalahaPlayer kalahaPlayer) {
        log.info("Checking if player turn {}", kalahaPlayer);
        boolean isPlayerTurn =  kalahaGame.getPlayerTurn().equals(kalahaPlayer);
        log.info("Player {} is in turn {}", kalahaPlayer, isPlayerTurn);
        return isPlayerTurn;
    }

    private PlayerTurn determinePlayerTurn(KalahaGame kalahaGame){
        return kalahaGameHelper.getPlayerTurn(kalahaGame);
    }
}
