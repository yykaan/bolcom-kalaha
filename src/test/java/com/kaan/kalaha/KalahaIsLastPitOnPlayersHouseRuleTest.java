package com.kaan.kalaha;

import com.kaan.kalaha.constant.KalahaGameConstants;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import com.kaan.kalaha.gameRules.KalahaGameFinishRule;
import com.kaan.kalaha.gameRules.KalahaIsLastPitOnPlayersHouseRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KalahaIsLastPitOnPlayersHouseRuleTest {
    @Mock
    KalahaGameFinishRule kalahaGameFinishRule;

    @Spy
    KalahaGameHelper kalahaGameHelper;

    @InjectMocks
    KalahaIsLastPitOnPlayersHouseRule kalahaIsLastPitOnPlayersHouseRule;

    @Test
    void shouldEvaluate_lastStonePutOnP1House() {
        KalahaGame kalahaGame = TestUtils.createGame();


        KalahaGame evaluatedGame = kalahaIsLastPitOnPlayersHouseRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), KalahaGameConstants.P1_STORE, PlayerTurn.P1);
        assertThat(kalahaGame).isEqualTo(evaluatedGame);
    }

    @Test
    void shouldNotEvaluate_lastStonePutOnP1House() {
        KalahaGame kalahaGame = TestUtils.createGame();

        kalahaIsLastPitOnPlayersHouseRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), KalahaGameConstants.P2_LOWER_BOUNDARY, PlayerTurn.P1);
    }
}
