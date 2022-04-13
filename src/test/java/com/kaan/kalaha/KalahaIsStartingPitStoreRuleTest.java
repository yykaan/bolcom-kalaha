package com.kaan.kalaha;

import com.kaan.kalaha.constant.KalahaGameConstants;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import com.kaan.kalaha.service.impl.rules.KalahaIsStartingPitStoreRule;
import com.kaan.kalaha.service.impl.rules.KalahaStoneSowingRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class KalahaIsStartingPitStoreRuleTest {
    @InjectMocks
    KalahaIsStartingPitStoreRule kalahaIsStartingPitStoreRule;

    @Spy
    KalahaGameHelper kalahaGameHelper;

    @Mock
    KalahaStoneSowingRule kalahaStoneSowingRule;

    @Test
    void shouldFail_p1StartFromOwnStore() {
        KalahaGame kalahaGame = TestUtils.createGame();

        KalahaGame evaluatedGame = kalahaIsStartingPitStoreRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), KalahaGameConstants.P1_STORE, PlayerTurn.P1);

        assertThat(evaluatedGame).isEqualTo(kalahaGame);
    }

    @Test
    void shouldEvaluate_p1StartFromValidPit() {
        KalahaGame kalahaGame = TestUtils.createGame();

        KalahaGame evaluatedGame = kalahaIsStartingPitStoreRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(),
                randomPit(KalahaGameConstants.P1_LOWER_BOUNDARY, KalahaGameConstants.P1_UPPER_BOUNDARY), PlayerTurn.P1);

        assertThat(evaluatedGame).isEqualTo(kalahaGame);
    }

    @Test
    void shouldFail_p2StartFromOwnStore() {
        KalahaGame kalahaGame = TestUtils.createGame();

        KalahaGame evaluatedGame = kalahaIsStartingPitStoreRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), KalahaGameConstants.P2_STORE, PlayerTurn.P2);

        assertThat(evaluatedGame).isEqualTo(kalahaGame);
    }

    @Test
    void shouldEvaluate_p2StartFromValidPit() {
        KalahaGame kalahaGame = TestUtils.createGame();

        KalahaGame evaluatedGame = kalahaIsStartingPitStoreRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(),
                randomPit(KalahaGameConstants.P2_LOWER_BOUNDARY, KalahaGameConstants.P2_UPPER_BOUNDARY), PlayerTurn.P1);

        assertThat(evaluatedGame).isEqualTo(kalahaGame);
    }

    private int randomPit(int lowerBoundary, int upperBoundary) {
        Random random = new Random();
        return random.ints(lowerBoundary, upperBoundary)
                .findFirst()
                .getAsInt();
    }
}
