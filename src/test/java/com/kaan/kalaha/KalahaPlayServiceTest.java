package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.exception.GameNotFoundException;
import com.kaan.kalaha.service.AuthService;
import com.kaan.kalaha.service.KalahaGameService;
import com.kaan.kalaha.service.KalahaPlayerService;
import com.kaan.kalaha.service.impl.KalahaPlayServiceImpl;
import com.kaan.kalaha.service.impl.rules.KalahaGameStartRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.kaan.kalaha.TestUtils.createGame;
import static com.kaan.kalaha.TestUtils.createKalahaPlayer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KalahaPlayServiceTest {

    @Mock
    KalahaGameService kalahaGameService;

    @Mock
    KalahaPlayerService kalahaPlayerService;

    @Mock
    KalahaGameStartRule kalahaGameStartRule;
    @Mock
    AuthService authService;

    @InjectMocks
    KalahaPlayServiceImpl kalahaPlayService;

    @Test
    public void test(){
        KalahaGame game = createGame();

        when(kalahaGameService.getGameById(game.getId()))
                .thenReturn(game);

        when(authService.getCurrentUser())
                .thenReturn(game.getFirstPlayer());

        when(kalahaPlayerService.getPlayerById(anyLong()))
                .thenReturn(createKalahaPlayer());

        when(kalahaGameStartRule.evaluate(game, createKalahaPlayer(), 1, null))
                .thenReturn(game);

        when(kalahaGameService.update(any(KalahaGame.class)))
                .thenReturn(game);

        KalahaGame evaluatedGame = kalahaPlayService.move(game.getId(), 1);

        assertThat(evaluatedGame.getPlayerTurn().getId()).isEqualTo(game.getFirstPlayer().getId());
    }

    @Test
    public void move_failThrowsGameNotFoundException(){
        assertThrows(GameNotFoundException.class,
                () -> kalahaPlayService.move(1L,1));
    }
}
