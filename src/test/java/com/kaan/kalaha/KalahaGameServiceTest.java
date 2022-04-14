package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.GameState;
import com.kaan.kalaha.exception.GameNotFoundException;
import com.kaan.kalaha.exception.UserNotLoggedInException;
import com.kaan.kalaha.repository.KalahaGameRepository;
import com.kaan.kalaha.service.AuthService;
import com.kaan.kalaha.service.KalahaBoardService;
import com.kaan.kalaha.service.KalahaPitService;
import com.kaan.kalaha.service.impl.KalahaGameServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kaan.kalaha.TestUtils.createKalahaPlayer;
import static com.kaan.kalaha.TestUtils.createKalahaPlayer2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KalahaGameServiceTest {

    @Mock
    KalahaGameRepository kalahaGameRepository;

    @Mock
    KalahaBoardService kalahaBoardService;

    @Mock
    KalahaPitService kalahaPitService;

    @Mock
    AuthService authService;

    @InjectMocks
    KalahaGameServiceImpl kalahaGameService;

    @Test
    public void createGameTest(){
        when(authService.getCurrentUser())
                .thenReturn(createKalahaPlayer());

        when(kalahaGameRepository.save(any(KalahaGame.class)))
                .thenReturn(new KalahaGame());

        KalahaGame newGame = kalahaGameService.createNewGame();

        assertThat(newGame).isNotNull();
    }

    @Test
    public void createGameTest_failUserNotLoggedIn(){
        assertThrows(UserNotLoggedInException.class,
                () -> kalahaGameService.createNewGame());
    }

    @Test
    public void updateGameTest_success(){
        KalahaGame kalahaGame = new KalahaGame();
        kalahaGame.setId(1L);

        when(kalahaGameRepository.existsById(anyLong()))
                .thenReturn(true);

        when(kalahaGameRepository.save(any(KalahaGame.class)))
                .thenReturn(kalahaGame);

        KalahaGame newGame = kalahaGameService.update(kalahaGame);

        assertThat(newGame).isNotNull();
    }

    @Test
    public void updateGameTest_failThrowsGameNotFoundException(){
        assertThrows(GameNotFoundException.class,
                () -> kalahaGameService.update(new KalahaGame()));
    }

    @Test
    public void joinGameTest_success(){
        KalahaGame kalahaGame = new KalahaGame();
        kalahaGame.setId(1L);

        when(kalahaGameRepository.findById(anyLong()))
                .thenReturn(Optional.of(kalahaGame));

        when(kalahaGameRepository.save(any(KalahaGame.class)))
                .thenReturn(kalahaGame);

        KalahaGame joinedGame = kalahaGameService.joinGame(createKalahaPlayer(), 1L);
        assertThat(joinedGame.getGameState()).isEqualTo(GameState.IN_PROGRESS);
    }

    @Test
    public void joinGameTest_fail_cannotJoinTheGameYouAlreadyIn(){
        KalahaGame kalahaGame = new KalahaGame();
        kalahaGame.setId(1L);
        kalahaGame.setFirstPlayer(createKalahaPlayer());

        when(kalahaGameRepository.findById(anyLong()))
                .thenReturn(Optional.of(kalahaGame));

        KalahaGame joinedGame = kalahaGameService.joinGame(createKalahaPlayer(), 1L);
        assertThat(joinedGame).isEqualTo(kalahaGame);
    }

    @Test
    public void joinGameTest_fail_throwsGameNotFoundException(){
        assertThrows(GameNotFoundException.class,
                () -> kalahaGameService.joinGame(createKalahaPlayer2(), 5L));

    }

    @Test
    public void updateGameState_success_stateSetToFinished(){
        KalahaGame kalahaGame = new KalahaGame();
        kalahaGame.setId(1L);
        kalahaGame.setGameState(GameState.IN_PROGRESS);

        when(kalahaGameRepository.findById(anyLong()))
                .thenReturn(Optional.of(kalahaGame));

        when(kalahaGameRepository.save(any(KalahaGame.class)))
                .thenReturn(kalahaGame);

        KalahaGame updateGameState = kalahaGameService.updateGameState(kalahaGame, GameState.FINISHED);
        assertThat(updateGameState.getGameState()).isEqualTo(GameState.FINISHED);
    }

    @Test
    public void getGamesToJoin_success(){
        final KalahaPlayer kalahaPlayer = createKalahaPlayer();

        ArrayList<KalahaGame> gamesToJoinList = new ArrayList<>();
        gamesToJoinList.add(new KalahaGame());

        when(kalahaGameRepository.findByGameState(GameState.WAITING_FOR_OTHER_PLAYER)
                .stream().filter(
                        kalahaGame -> kalahaGame.getFirstPlayer() != kalahaPlayer
                ).collect(Collectors.toList()))
                .thenReturn(gamesToJoinList);
        List<KalahaGame> gamesToJoin = kalahaGameService.getGamesToJoin(createKalahaPlayer());

        assertThat(gamesToJoin).hasSize(1);
    }
}
