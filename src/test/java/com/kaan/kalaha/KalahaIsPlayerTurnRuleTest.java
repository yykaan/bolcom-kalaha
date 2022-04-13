package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import com.kaan.kalaha.gameRules.KalahaIsGameInProgressRule;
import com.kaan.kalaha.gameRules.KalahaIsPlayerInTurnRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class KalahaIsPlayerTurnRuleTest {
    @Mock
    KalahaIsGameInProgressRule kalahaIsGameInProgressRule;

    @Spy
    KalahaGameHelper kalahaGameHelper;

    @InjectMocks
    KalahaIsPlayerInTurnRule isPlayerInTurnRule;

    @Test
    void p1Plays_shouldReturnP1_TurnIsP1() {
        KalahaGame kalahaGame = TestUtils.createGame();

        kalahaGame.setPlayerTurn(kalahaGame.getFirstPlayer());

        KalahaGame evaluatedGame = isPlayerInTurnRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), 1, PlayerTurn.P1);

        assertThat(evaluatedGame.getPlayerTurn()).isEqualTo(kalahaGame.getFirstPlayer());
    }

    @Test
    void p2Plays_shouldReturnP1_TurnIsP1() {
        KalahaGame kalahaGame = TestUtils.createGame();

        kalahaGame.setPlayerTurn(kalahaGame.getSecondPlayer());

        KalahaGame evaluatedGame = isPlayerInTurnRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), 2, PlayerTurn.P2);

        assertThat(evaluatedGame.getPlayerTurn()).isNotEqualTo(kalahaGame.getFirstPlayer());
    }
}
