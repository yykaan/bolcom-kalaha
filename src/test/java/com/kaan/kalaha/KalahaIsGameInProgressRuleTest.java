package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.enums.GameState;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.gameRules.KalahaIsGameInProgressRule;
import com.kaan.kalaha.gameRules.KalahaValidateStartingPositionAndStoneCountByTurnRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class KalahaIsGameInProgressRuleTest {
    @InjectMocks
    KalahaIsGameInProgressRule kalahaIsGameInProgressRule;

    @Mock
    KalahaValidateStartingPositionAndStoneCountByTurnRule kalahaValidateStartingPositionAndStoneCountByTurnRule;

    @Test
    void shouldSuccess_gameIsInProgress() {
        KalahaGame kalahaGame = TestUtils.createGame();

        KalahaGame evaluatedGame = kalahaIsGameInProgressRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), 1, PlayerTurn.P1);

        assertThat(evaluatedGame.getGameState()).isEqualTo(GameState.IN_PROGRESS);
    }

    @Test
    void shouldFail_gameIsFinished() {
        KalahaGame kalahaGame = TestUtils.createGame();

        kalahaGame.setGameState(GameState.FINISHED);

        KalahaGame evaluatedGame = kalahaIsGameInProgressRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), 2, PlayerTurn.P2);

        assertThat(evaluatedGame.getGameState()).isNotEqualTo(GameState.IN_PROGRESS);
    }
}
