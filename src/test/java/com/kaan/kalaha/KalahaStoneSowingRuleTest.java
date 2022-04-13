package com.kaan.kalaha;

import com.google.common.collect.Iterables;
import com.kaan.kalaha.constant.KalahaGameConstants;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.exception.NoStoneToSowException;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import com.kaan.kalaha.gameRules.KalahaCaptureRule;
import com.kaan.kalaha.gameRules.KalahaStoneSowingRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class KalahaStoneSowingRuleTest {
    @Mock
    KalahaCaptureRule kalahaCaptureRule;

    @Spy
    KalahaGameHelper kalahaGameHelper;

    @InjectMocks
    KalahaStoneSowingRule kalahaStoneSowingRule;

    @Test
    void shouldFail_p1TryToSowFromPit_HasNoStone_throwsNoStoneToSowException() {
        KalahaGame kalahaGame = TestUtils.createGame();

        int position = randomPit(KalahaGameConstants.P1_LOWER_BOUNDARY, KalahaGameConstants.P1_UPPER_BOUNDARY);

        emptyPitStone(kalahaGame, position);

        assertThrows(NoStoneToSowException.class,
                () -> kalahaStoneSowingRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(),
                        position, PlayerTurn.P1));
    }

    @Test
    void shouldFail_p2TryToSowFromPit_HasNoStone_throwsNoStoneToSowException() {
        KalahaGame kalahaGame = TestUtils.createGame();

        int position = randomPit(KalahaGameConstants.P2_LOWER_BOUNDARY, KalahaGameConstants.P2_UPPER_BOUNDARY);

        emptyPitStone(kalahaGame, position);

        assertThrows(NoStoneToSowException.class,
                () -> kalahaStoneSowingRule.evaluate(kalahaGame, kalahaGame.getSecondPlayer(),
                        position, PlayerTurn.P2));
    }

    @Test
    void shouldEvaluate_p1SowFirstPit_p1StoreShouldHaveOneStone() {
        KalahaGame kalahaGame = TestUtils.createGame();

        KalahaGame evaluatedGame = kalahaStoneSowingRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(),
                KalahaGameConstants.P1_LOWER_BOUNDARY, PlayerTurn.P1);

        assertThat(getPitByIndex(evaluatedGame, KalahaGameConstants.P1_STORE).getStones()).isEqualTo(1);

        assertThat(evaluatedGame.getPlayerTurn()).isEqualTo(kalahaGame.getPlayerTurn());
    }

    @Test
    void shouldEvaluate_p2SowFirstPit_p2StoreShouldHaveOneStone() {
        KalahaGame kalahaGame = TestUtils.createGame();

        KalahaGame evaluatedGame = kalahaStoneSowingRule.evaluate(kalahaGame, kalahaGame.getSecondPlayer(),
                KalahaGameConstants.P2_LOWER_BOUNDARY, PlayerTurn.P2);

        assertThat(getPitByIndex(evaluatedGame, KalahaGameConstants.P2_STORE).getStones()).isEqualTo(1);

        assertThat(evaluatedGame.getPlayerTurn()).isEqualTo(kalahaGame.getPlayerTurn());
    }

    @Test
    void shouldEvaluate_p1SowSecondPit_p1StoreShouldHaveOneStone() {
        KalahaGame kalahaGame = TestUtils.createGame();

        KalahaGame evaluatedGame = kalahaStoneSowingRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(),
                KalahaGameConstants.P1_LOWER_BOUNDARY + 1, PlayerTurn.P1);

        assertThat(getPitByIndex(evaluatedGame, KalahaGameConstants.P1_STORE).getStones()).isEqualTo(1);

        assertThat(getPitByIndex(evaluatedGame, KalahaGameConstants.P2_LOWER_BOUNDARY).getStones()).isEqualTo(7);
    }

    @Test
    void shouldEvaluate_p2SowSecondPit_p2StoreShouldHaveOneStone() {
        KalahaGame kalahaGame = TestUtils.createGame();

        KalahaGame evaluatedGame = kalahaStoneSowingRule.evaluate(kalahaGame, kalahaGame.getSecondPlayer(),
                KalahaGameConstants.P2_LOWER_BOUNDARY + 1, PlayerTurn.P2);

        assertThat(getPitByIndex(evaluatedGame, KalahaGameConstants.P2_STORE).getStones()).isEqualTo(1);

        assertThat(getPitByIndex(evaluatedGame, KalahaGameConstants.P1_LOWER_BOUNDARY).getStones()).isEqualTo(7);
    }

    private int randomPit(int lowerBoundary, int upperBoundary) {
        Random random = new Random();
        return random.ints(lowerBoundary, upperBoundary)
                .findFirst()
                .getAsInt();
    }

    private KalahaPit getPitByIndex(KalahaGame kalahaGame, int index) {
        return Iterables.getOnlyElement(kalahaGame.getKalahaBoard().getPits().stream()
                .filter(kalahaGameHelper.getGetPitByPosition(index))
                .collect(Collectors.toList()));
    }

    private void emptyPitStone(KalahaGame kalahaGame, int position) {
        KalahaPit pit = Iterables.getOnlyElement(kalahaGame.getKalahaBoard().getPits().stream()
                .filter(kalahaGameHelper.getGetPitByPosition(position))
                .collect(Collectors.toList()));

        pit.setStones(0);
    }
}
