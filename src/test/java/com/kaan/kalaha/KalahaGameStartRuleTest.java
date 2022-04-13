package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.impl.rules.KalahaGameStartRule;
import com.kaan.kalaha.service.impl.rules.KalahaIsPlayerInTurnRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KalahaGameStartRuleTest {

    @Mock
    KalahaIsPlayerInTurnRule kalahaIsPlayerInTurnRule;

    @InjectMocks
    KalahaGameStartRule kalahaGameStartRule;

    @Test
    void shouldReturnFirstPlayer_LastPitIsOnOwnHouse() {
        KalahaGame kalahaGame = TestUtils.createGame();

        kalahaGame.setPlayerTurn(kalahaGame.getFirstPlayer());

        when(kalahaGameStartRule.getNextRule().evaluate(kalahaGame, kalahaGame.getFirstPlayer(), 1, null))
                .thenReturn(kalahaGame);

        KalahaGame evaluatedGame = kalahaGameStartRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), 1, null);

        assertThat(evaluatedGame.getPlayerTurn()).isEqualTo(kalahaGame.getFirstPlayer());
    }

    @Test
    void shouldReturnSecond_LastPitIsNotOnOwnHouse() {
        KalahaGame kalahaGame = TestUtils.createGame();

        kalahaGame.setPlayerTurn(kalahaGame.getSecondPlayer());

        when(kalahaGameStartRule.getNextRule().evaluate(kalahaGame, kalahaGame.getFirstPlayer(), 2, null))
                .thenReturn(kalahaGame);

        KalahaGame evaluatedGame = kalahaGameStartRule.evaluate(kalahaGame, kalahaGame.getFirstPlayer(), 2, null);

        assertThat(evaluatedGame.getPlayerTurn()).isNotEqualTo(kalahaGame.getFirstPlayer());
    }

}
