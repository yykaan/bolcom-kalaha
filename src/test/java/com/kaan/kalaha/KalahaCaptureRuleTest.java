package com.kaan.kalaha;

import com.kaan.kalaha.constant.KalahaGameConstants;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import com.kaan.kalaha.service.impl.rules.KalahaCaptureRule;
import com.kaan.kalaha.service.impl.rules.KalahaIsLastPitOnPlayersHouseRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.kaan.kalaha.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class KalahaCaptureRuleTest {
    @InjectMocks
    KalahaCaptureRule kalahaCaptureRule;

    @Spy
    KalahaGameHelper kalahaGameHelper;

    @Mock
    KalahaIsLastPitOnPlayersHouseRule kalahaIsLastPitOnPlayersHouseRule;

    @Test
    void shouldNotEvaluate_OppositePitHasNoStone() {
        KalahaGame kalahaGame = TestUtils.createGame();

        int position = 1;

        KalahaPit oppositePit = getOppositePit(kalahaGame, position, kalahaGameHelper);

        emptyPitStone(kalahaGame, oppositePit.getPosition(), kalahaGameHelper);

        assertThat(oppositePit.getStones() == 0 ).isTrue();

        kalahaCaptureRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), position, PlayerTurn.P1);

        assertThat(getPitByIndex(kalahaGame, KalahaGameConstants.P1_STORE, kalahaGameHelper).getStones() == 0).isTrue();
    }

    @Test
    void shouldEvaluate_OppositePitHasStone() {
        KalahaGame kalahaGame = TestUtils.createGame();

        int position = 1;

        recreateCaptureRuleScenario(kalahaGame, position, kalahaGameHelper);

        KalahaPit oppositePit = getOppositePit(kalahaGame, position, kalahaGameHelper);

        assertThat(oppositePit.getStones() == 0 ).isFalse();

        kalahaCaptureRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), position, PlayerTurn.P1);

        assertThat(getPitByIndex(kalahaGame, KalahaGameConstants.P1_STORE, kalahaGameHelper).getStones() > 0).isTrue();
    }
}
