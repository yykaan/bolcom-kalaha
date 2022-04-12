package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.exception.GameNotFoundException;
import com.kaan.kalaha.exception.UserNotLoggedInException;
import com.kaan.kalaha.repository.KalahaGameRepository;
import com.kaan.kalaha.service.AuthService;
import com.kaan.kalaha.service.impl.KalahaGameServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    }

    private KalahaPlayer createKalahaPlayer(){
        return new KalahaPlayer("bolcomtest","a@bol.com","strongpassword");
    }
}
