package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.GameState;
import com.kaan.kalaha.enums.PitType;
import com.kaan.kalaha.repository.KalahaBoardRepository;
import com.kaan.kalaha.repository.KalahaPitRepository;
import com.kaan.kalaha.service.AuthService;
import com.kaan.kalaha.service.KalahaGameService;
import com.kaan.kalaha.service.KalahaPlayerService;
import com.kaan.kalaha.service.impl.KalahaPitServiceImpl;
import com.kaan.kalaha.service.impl.KalahaPlayServiceImpl;
import com.kaan.kalaha.service.impl.rules.KalahaGameStartRule;
import com.kaan.kalaha.service.impl.rules.KalahaIsPlayerInTurnRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.kaan.kalaha.constant.KalahaGameConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KalahaPlayServiceTest {

    @Mock
    KalahaGameService kalahaGameService;

    @Mock
    KalahaPlayerService kalahaPlayerService;

    @InjectMocks
    KalahaGameStartRule kalahaGameStartRule;

    @Mock
    KalahaIsPlayerInTurnRule kalahaIsPlayerInTurnRule;

    @Mock
    AuthService authService;

    @InjectMocks
    KalahaPlayServiceImpl kalahaPlayService;

    @Test
    public void test(){
        KalahaGame game = createGame();

        KalahaGame evaluatedGame = kalahaGameStartRule.evaluate(game, createKalahaPlayer(), 1, null);
        assertThat(game).isNotEqualTo(evaluatedGame);
    }

    public KalahaGame createGame(){
        KalahaGame kalahaGame = new KalahaGame();
        kalahaGame.setId(1L);
        kalahaGame.setGameState(GameState.IN_PROGRESS);
        kalahaGame.setFirstPlayer(createKalahaPlayer());
        kalahaGame.setSecondPlayer(createKalahaPlayer2());
        kalahaGame.setPlayerTurn(createKalahaPlayer());
        kalahaGame.setKalahaBoard(createBoard());
        return kalahaGame;
    }

    private KalahaPlayer createKalahaPlayer(){
        KalahaPlayer kalahaPlayer = new KalahaPlayer("bolcomtest","a@bol.com","strongpassword");
        kalahaPlayer.setId(1L);
        return kalahaPlayer;
    }

    private KalahaPlayer createKalahaPlayer2(){
        KalahaPlayer kalahaPlayer = new KalahaPlayer("bolcomtest2","b@bol.com","strongpassword");
        kalahaPlayer.setId(2L);
        return kalahaPlayer;
    }

    private KalahaBoard createBoard(){
        KalahaBoard kalahaBoard = new KalahaBoard();
        kalahaBoard.setPits(createPits(kalahaBoard));
        return kalahaBoard;
    }

    public List<KalahaPit> createPits(KalahaBoard kalahaBoard){
        List<KalahaPit> pits = new ArrayList<>();
        pits.add(createPit(kalahaBoard, PitType.HOUSE, P1_STORE, HOUSE_STONE_INITIAL_COUNT));
        pits.add(createPit(kalahaBoard, PitType.HOUSE, P2_STORE, HOUSE_STONE_INITIAL_COUNT));

        // P1 houses
        for (int i = P1_LOWER_BOUNDARY; i <= P1_UPPER_BOUNDARY; i++) {
            pits.add(createPit(kalahaBoard, PitType.PIT, i, PIT_STONE_INITIAL_COUNT));
        }

        // P2 houses
        for (int i = P2_LOWER_BOUNDARY; i <= P2_UPPER_BOUNDARY; i++) {
            pits.add(createPit(kalahaBoard, PitType.PIT, i, PIT_STONE_INITIAL_COUNT));
        }
        return pits;
    }

    private KalahaPit createPit(KalahaBoard kalahaBoard, PitType pitType, int position, int nrOfStones) {
        return new KalahaPit(kalahaBoard, position, nrOfStones, pitType);
    }
}
