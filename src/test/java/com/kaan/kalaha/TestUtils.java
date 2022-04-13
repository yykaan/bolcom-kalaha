package com.kaan.kalaha;

import com.google.common.collect.Iterables;
import com.kaan.kalaha.dto.LoginRequest;
import com.kaan.kalaha.dto.RegisterRequest;
import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.GameState;
import com.kaan.kalaha.enums.PitType;
import com.kaan.kalaha.security.model.SecurityUser;
import com.kaan.kalaha.service.impl.KalahaGameHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kaan.kalaha.constant.KalahaGameConstants.*;

public class TestUtils {

    public static KalahaGame createGame(){
        KalahaPlayer player1 = createKalahaPlayer();
        KalahaPlayer player2 = createKalahaPlayer2();

        KalahaGame kalahaGame = new KalahaGame();
        kalahaGame.setId(1L);
        kalahaGame.setGameState(GameState.IN_PROGRESS);
        kalahaGame.setFirstPlayer(player1);
        kalahaGame.setSecondPlayer(player2);
        kalahaGame.setPlayerTurn(player1);
        kalahaGame.setKalahaBoard(createBoard());
        return kalahaGame;
    }

    public static KalahaPlayer createKalahaPlayer(){
        KalahaPlayer kalahaPlayer = new KalahaPlayer("bolcomtest","a@bol.com","strongpassword");
        kalahaPlayer.setId(1L);
        return kalahaPlayer;
    }

    public static KalahaPlayer createKalahaPlayer2(){
        KalahaPlayer kalahaPlayer = new KalahaPlayer("bolcomtest2","b@bol.com","strongpassword");
        kalahaPlayer.setId(2L);
        return kalahaPlayer;
    }

    public static KalahaBoard createBoard(){
        KalahaBoard kalahaBoard = new KalahaBoard();
        kalahaBoard.setPits(createPits(kalahaBoard));
        return kalahaBoard;
    }

    public static List<KalahaPit> createPits(KalahaBoard kalahaBoard){
        List<KalahaPit> pits = new ArrayList<>();
        pits.add(createPit(kalahaBoard, PitType.HOUSE, P1_STORE, HOUSE_STONE_INITIAL_COUNT));
        pits.add(createPit(kalahaBoard, PitType.HOUSE, P2_STORE, HOUSE_STONE_INITIAL_COUNT));

        for (int i = P1_LOWER_BOUNDARY; i <= P1_UPPER_BOUNDARY; i++) {
            pits.add(createPit(kalahaBoard, PitType.PIT, i, PIT_STONE_INITIAL_COUNT));
        }

        for (int i = P2_LOWER_BOUNDARY; i <= P2_UPPER_BOUNDARY; i++) {
            pits.add(createPit(kalahaBoard, PitType.PIT, i, PIT_STONE_INITIAL_COUNT));
        }
        return pits;
    }

    public static KalahaPit createPit(KalahaBoard kalahaBoard, PitType pitType, int position, int nrOfStones) {
        return new KalahaPit(kalahaBoard, position, nrOfStones, pitType);
    }

    public static SecurityUser createSecurityUser(){
        return new SecurityUser("bolcomtest");
    }

    public static RegisterRequest createRegisterRequest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("a@bol.com");
        registerRequest.setUsername("bolcomtest");
        registerRequest.setPassword("strongpassword");
        return registerRequest;
    }

    public static LoginRequest createLoginRequest(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("bolcomtest");
        loginRequest.setPassword("strongpassword");
        return loginRequest;
    }

    public static KalahaPit getOppositePit(KalahaGame kalahaGame, int position, KalahaGameHelper kalahaGameHelper) {
        return kalahaGame.getKalahaBoard().getPits().stream()
                .filter(kalahaGameHelper.getGetPitByPosition(kalahaGameHelper.getOppositePitIndex(position)))
                .findFirst().get();
    }

    public static KalahaPit getPitByIndex(KalahaGame kalahaGame, int index, KalahaGameHelper kalahaGameHelper) {
        return Iterables.getOnlyElement(kalahaGame.getKalahaBoard().getPits().stream()
                .filter(kalahaGameHelper.getGetPitByPosition(index))
                .collect(Collectors.toList()));
    }

    public static void emptyPitStone(KalahaGame kalahaGame, int position, KalahaGameHelper kalahaGameHelper) {
        KalahaPit pit = Iterables.getOnlyElement(kalahaGame.getKalahaBoard().getPits().stream()
                .filter(kalahaGameHelper.getGetPitByPosition(position))
                .collect(Collectors.toList()));

        pit.setStones(0);
    }

    public static void emptyAllPits(KalahaGame kalahaGame) {
        kalahaGame.getKalahaBoard().getPits().forEach(pit -> pit.setStones(0));
    }

    public static void addStoneToIndex(KalahaGame kalahaGame, int index, KalahaGameHelper kalahaGameHelper) {
        KalahaPit pit = Iterables.getOnlyElement(kalahaGame.getKalahaBoard().getPits().stream()
                .filter(kalahaGameHelper.getGetPitByPosition(index))
                .collect(Collectors.toList()));

        pit.setStones(1);
    }

    public static void addStoneToIndex(KalahaGame kalahaGame, int index, int stoneCount, KalahaGameHelper kalahaGameHelper) {
        KalahaPit pit = Iterables.getOnlyElement(kalahaGame.getKalahaBoard().getPits().stream()
                .filter(kalahaGameHelper.getGetPitByPosition(index))
                .collect(Collectors.toList()));

        pit.setStones(stoneCount);
    }

    public static void addStoneToOppositePit(KalahaGame kalahaGame, int position, KalahaGameHelper kalahaGameHelper) {
        KalahaPit pit = Iterables.getOnlyElement(kalahaGame.getKalahaBoard().getPits().stream()
                .filter(kalahaGameHelper.getGetPitByPosition(kalahaGameHelper.getOppositePitIndex(position)))
                .collect(Collectors.toList()));

        pit.setStones(1);
    }

    public static void recreateCaptureRuleScenario(KalahaGame kalahaGame, int position, KalahaGameHelper kalahaGameHelper) {
        emptyAllPits(kalahaGame);
        addStoneToIndex(kalahaGame, position, kalahaGameHelper);
        addStoneToOppositePit(kalahaGame, position, kalahaGameHelper);
    }
}
