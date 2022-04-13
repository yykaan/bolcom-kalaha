package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import com.kaan.kalaha.gameRules.KalahaIsStartingPitStoreRule;
import com.kaan.kalaha.gameRules.KalahaValidateStartingPositionAndStoneCountByTurnRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class KalahaValidateStartingPositionAndStoneCountByTurnRuleTest {
    @Mock
    KalahaIsStartingPitStoreRule kalahaIsStartingPitStoreRule;

    @Spy
    KalahaGameHelper kalahaGameHelper;

    @InjectMocks
    KalahaValidateStartingPositionAndStoneCountByTurnRule kalahaValidateStartingPositionAndStoneCountByTurnRule;

    @Test
    void pitHasZeroStones_shouldReturnSameGame_WithoutEvaluating() {
        KalahaGame kalahaGame = TestUtils.createGame();

        kalahaGame.getKalahaBoard().getPits().get(13).setStones(0);

        KalahaGame evaluatedGame = kalahaValidateStartingPositionAndStoneCountByTurnRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), 13, PlayerTurn.P1);

        assertThat(evaluatedGame).isEqualTo(kalahaGame);
    }

    @Test
    void pitHasStones_startingPitIsOwnPit_shouldEvaluate() {
        KalahaGame kalahaGame = TestUtils.createGame();

        KalahaGame evaluatedGame = kalahaValidateStartingPositionAndStoneCountByTurnRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), 1, PlayerTurn.P1);

        assertThat(evaluatedGame).isEqualTo(kalahaGame);
    }
}
