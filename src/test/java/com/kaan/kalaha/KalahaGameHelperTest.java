package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.kaan.kalaha.constant.KalahaGameConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class KalahaGameHelperTest {

    @InjectMocks
    KalahaGameHelper kalahaGameHelper;

    @Test
    public void validateStartingPitPositionByPlayerTurn_p1ValidPosition(){
        Boolean valid = kalahaGameHelper.validateStartingPitPositionByPlayerTurnAndPitHasStone(PlayerTurn.P1, 3, 1);

        assertThat(valid).isTrue();
    }

    @Test
    public void validateStartingPitPositionByPlayerTurn_p1NotValidPosition(){
        Boolean valid = kalahaGameHelper.validateStartingPitPositionByPlayerTurnAndPitHasStone(PlayerTurn.P1, 8, 1);

        assertThat(valid).isFalse();
    }

    @Test
    public void validateStartingPitPositionByPlayerTurn_p1ValidPosition_invalidStoneCount(){
        Boolean valid = kalahaGameHelper.validateStartingPitPositionByPlayerTurnAndPitHasStone(PlayerTurn.P1, 3, 0);

        assertThat(valid).isFalse();
    }

    @Test
    public void validateStartingPitPositionByPlayerTurn_p2ValidPosition(){
        Boolean valid = kalahaGameHelper.validateStartingPitPositionByPlayerTurnAndPitHasStone(PlayerTurn.P2, 8, 1);

        assertThat(valid).isTrue();
    }

    @Test
    public void validateStartingPitPositionByPlayerTurn_p2NotValidPosition(){
        Boolean valid = kalahaGameHelper.validateStartingPitPositionByPlayerTurnAndPitHasStone(PlayerTurn.P2, 1, 1);

        assertThat(valid).isFalse();
    }

    @Test
    public void validateStartingPitPositionByPlayerTurn_p2ValidPosition_invalidStoneCount(){
        Boolean valid = kalahaGameHelper.validateStartingPitPositionByPlayerTurnAndPitHasStone(PlayerTurn.P2, 11, 0);

        assertThat(valid).isFalse();
    }

    @Test
    public void getPlayerStoreIndexByPlayerTurn_p1(){
        Integer valid = kalahaGameHelper.getPlayerStoreIndexByPlayerTurn(PlayerTurn.P1);

        assertThat(valid).isEqualTo(P1_STORE);
    }

    @Test
    public void getPlayerStoreIndexByPlayerTurn_p2(){
        Integer valid = kalahaGameHelper.getPlayerStoreIndexByPlayerTurn(PlayerTurn.P2);

        assertThat(valid).isEqualTo(P2_STORE);
    }

    @Test
    public void getLowerPositionByPlayerTurn_p1(){
        Integer valid = kalahaGameHelper.getLowerPositionByPlayerTurn(PlayerTurn.P1);

        assertThat(valid).isEqualTo(P1_LOWER_BOUNDARY);
    }

    @Test
    public void getLowerPositionByPlayerTurn_p2(){
        Integer valid = kalahaGameHelper.getLowerPositionByPlayerTurn(PlayerTurn.P2);

        assertThat(valid).isEqualTo(P2_LOWER_BOUNDARY);
    }

    @Test
    public void getUpperPositionByPlayerTurn_p1(){
        Integer valid = kalahaGameHelper.getUpperPositionByPlayerTurn(PlayerTurn.P1);

        assertThat(valid).isEqualTo(P1_UPPER_BOUNDARY);
    }

    @Test
    public void getUpperPositionByPlayerTurn_p2(){
        Integer valid = kalahaGameHelper.getUpperPositionByPlayerTurn(PlayerTurn.P2);

        assertThat(valid).isEqualTo(P2_UPPER_BOUNDARY);
    }

    @Test
    public void getOppositePitIndex(){
        Integer valid = kalahaGameHelper.getOppositePitIndex(5);

        assertThat(valid).isEqualTo(TOTAL_PIT_COUNT - 5);
    }

    @Test
    public void isStartingPitStore_notStartingFromHouse(){
        Boolean valid = kalahaGameHelper.isStartingPitStore(5);

        assertThat(valid).isFalse();
    }

    @Test
    public void isStartingPitStore_fail_startingFromP1House(){
        Boolean valid = kalahaGameHelper.isStartingPitStore(P1_STORE);

        assertThat(valid).isTrue();
    }

    @Test
    public void isStartingPitStore_fail_startingFromP2House(){
        Boolean valid = kalahaGameHelper.isStartingPitStore(P2_STORE);

        assertThat(valid).isTrue();
    }

    @Test
    public void getPlayerTurn_returnP1_firstMoveAtGame(){
        PlayerTurn playerTurn = kalahaGameHelper.getPlayerTurn(new KalahaGame());

        assertThat(playerTurn).isEqualTo(PlayerTurn.P1);
    }

    @Test
    public void getPlayerTurn_returnP1_turnIsP1(){
        KalahaGame kalahaGame = new KalahaGame();
        KalahaPlayer firstPlayer = new KalahaPlayer();
        kalahaGame.setFirstPlayer(firstPlayer);
        kalahaGame.setPlayerTurn(firstPlayer);
        PlayerTurn playerTurn = kalahaGameHelper.getPlayerTurn(kalahaGame);

        assertThat(playerTurn).isEqualTo(PlayerTurn.P1);
    }

    @Test
    public void getPlayerTurn_returnP2_turnIsP2(){
        KalahaGame kalahaGame = new KalahaGame();

        KalahaPlayer firstPlayer = new KalahaPlayer();
        firstPlayer.setId(1L);

        KalahaPlayer secondPlayer = new KalahaPlayer();
        secondPlayer.setId(2L);

        kalahaGame.setFirstPlayer(firstPlayer);
        kalahaGame.setSecondPlayer(secondPlayer);

        kalahaGame.setPlayerTurn(secondPlayer);
        PlayerTurn playerTurn = kalahaGameHelper.getPlayerTurn(kalahaGame);

        assertThat(playerTurn).isEqualTo(PlayerTurn.P2);
    }
}
