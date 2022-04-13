package com.kaan.kalaha;

import com.kaan.kalaha.constant.KalahaGameConstants;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.enums.GameState;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import com.kaan.kalaha.gameRules.KalahaPostFinishGameRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.kaan.kalaha.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class KalahaPostFinishedGameRuleTest {
    @Spy
    KalahaGameHelper kalahaGameHelper;

    @InjectMocks
    KalahaPostFinishGameRule kalahaPostFinishGameRule;

    @Test
    void shouldEvaluate_allPitsAreEmpty_onlyHousesHaveStones_grabbingStonesFromOpponentSide() {
        KalahaGame kalahaGame = TestUtils.createGame();

        emptyAllPits(kalahaGame);
        addStoneToIndex(kalahaGame, KalahaGameConstants.P1_STORE, 9, kalahaGameHelper);
        addStoneToIndex(kalahaGame, KalahaGameConstants.P2_STORE, 10, kalahaGameHelper);
        addStoneToIndex(kalahaGame, KalahaGameConstants.P2_LOWER_BOUNDARY, 10, kalahaGameHelper);

        kalahaGame.setGameState(GameState.FINISHED);

        KalahaGame evaluatedGame = kalahaPostFinishGameRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), KalahaGameConstants.P1_LOWER_BOUNDARY, PlayerTurn.P1);

        assertThat(evaluatedGame.getGameState()).isEqualTo(GameState.FINISHED);
        assertThat(getPitByIndex(evaluatedGame, KalahaGameConstants.P1_STORE, kalahaGameHelper).getStones()).isEqualTo(19);
        assertThat(getPitByIndex(evaluatedGame, KalahaGameConstants.P2_STORE, kalahaGameHelper).getStones()).isEqualTo(10);

        assertThat(kalahaPostFinishGameRule.getNextRule()).isNull();
    }
}
