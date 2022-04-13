package com.kaan.kalaha;

import com.kaan.kalaha.constant.KalahaGameConstants;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.enums.GameState;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import com.kaan.kalaha.service.impl.rules.KalahaGameFinishRule;
import com.kaan.kalaha.service.impl.rules.KalahaIsLastPitOnPlayersHouseRule;
import com.kaan.kalaha.service.impl.rules.KalahaPostFinishGameRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.kaan.kalaha.TestUtils.addStoneToIndex;
import static com.kaan.kalaha.TestUtils.emptyAllPits;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KalahaGameFinishRuleTest {
    @InjectMocks
    KalahaGameFinishRule kalahaGameFinishRule;

    @Spy
    KalahaGameHelper kalahaGameHelper;

    @Mock
    KalahaPostFinishGameRule kalahaPostFinishGameRule;

    @Test
    void shouldEvaluate_allPitsAreEmpty_onlyHousesHaveStones() {
        KalahaGame kalahaGame = TestUtils.createGame();

        emptyAllPits(kalahaGame);
        addStoneToIndex(kalahaGame, KalahaGameConstants.P1_STORE, 9, kalahaGameHelper);
        addStoneToIndex(kalahaGame, KalahaGameConstants.P2_STORE, 10, kalahaGameHelper);

        kalahaGame.setGameState(GameState.FINISHED);

        KalahaGame evaluatedGame = kalahaGameFinishRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), KalahaGameConstants.P1_LOWER_BOUNDARY, PlayerTurn.P1);

        assertThat(evaluatedGame.getGameState()).isEqualTo(GameState.FINISHED);
    }

    @Test
    void shouldNotEvaluate_lastStonePutOnP1House() {
        KalahaGame kalahaGame = TestUtils.createGame();

        KalahaGame evaluatedGame = kalahaGameFinishRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), KalahaGameConstants.P2_LOWER_BOUNDARY, PlayerTurn.P1);

        assertThat(evaluatedGame.getGameState()).isEqualTo(GameState.IN_PROGRESS);
    }
}
