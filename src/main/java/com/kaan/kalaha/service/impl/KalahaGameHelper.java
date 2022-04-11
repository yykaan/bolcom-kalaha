package com.kaan.kalaha.service.impl;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.enums.PitType;
import com.kaan.kalaha.enums.PlayerTurn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

import static com.kaan.kalaha.constant.KalahaGameConstants.*;

@Service
@Slf4j
public class KalahaGameHelper {

    private int lowerBound;
    private int upperBound;
    private int position;
    private final Predicate<KalahaPit> getPlayerPits = kalahaPit -> kalahaPit.getPosition() >= lowerBound && kalahaPit.getPosition() <= upperBound;
    private final Predicate<KalahaPit> getPlayerOnlyPits = kalahaPit -> kalahaPit.getPitType() == PitType.PIT;
    private final Predicate<KalahaPit> getPlayerHouse = kalahaPit -> kalahaPit.getPitType() == PitType.HOUSE;
    private final Predicate<KalahaPit> isPitHasStone = kalahaPit -> kalahaPit.getStones() > 0;
    private final Predicate<KalahaPit> getPitByPosition = kalahaPit -> kalahaPit.getPosition() == position;

    public Boolean validateStartingPitPositionByPlayerTurn(PlayerTurn playerTurn, int position, int stones) {
        return switch (playerTurn) {
            case P1 -> position >= P1_LOWER_BOUNDARY && position <= P1_UPPER_BOUNDARY && stones > 0;
            case P2 -> position >= P2_LOWER_BOUNDARY && position <= P2_UPPER_BOUNDARY && stones > 0;
        };
    }

    public Integer getPlayerStoreIndexByPlayerTurn(PlayerTurn playerTurn) {
        return switch (playerTurn) {
            case P1 -> P1_STORE;
            case P2 -> P2_STORE;
        };
    }

    public Integer getLowerPositionByPlayerTurn(PlayerTurn playerTurn) {
        return switch (playerTurn) {
            case P1 -> P1_LOWER_BOUNDARY;
            case P2 -> P2_LOWER_BOUNDARY;
        };
    }

    public Integer getUpperPositionByPlayerTurn(PlayerTurn playerTurn) {
        return switch (playerTurn) {
            case P1 -> P1_UPPER_BOUNDARY;
            case P2 -> P2_UPPER_BOUNDARY;
        };
    }

    public Integer getOppositePitIndex(int position){
        return TOTAL_PIT_COUNT - position;
    }

    public Boolean isStartingPitStore(int position){
        return position == P1_STORE || position == P2_STORE;
    }

    public PlayerTurn getPlayerTurn(KalahaGame kalahaGame){
        if (kalahaGame.getPlayerTurn() == null){
            return PlayerTurn.P1;
        }else {
            if (kalahaGame.getPlayerTurn().equals(kalahaGame.getFirstPlayer())){
                return PlayerTurn.P1;
            }else {
                return PlayerTurn.P2;
            }
        }
    }

    public Predicate<KalahaPit> getPlayerPits(PlayerTurn playerTurn) {
        this.lowerBound = getLowerPositionByPlayerTurn(playerTurn);
        this.upperBound = getUpperPositionByPlayerTurn(playerTurn);
        return getPlayerPits;
    }

    public Predicate<KalahaPit> getGetPlayerOnlyPits(){
        return getPlayerOnlyPits;
    }

    public Predicate<KalahaPit> getIsPitHasStone(){
        return isPitHasStone;
    }

    public Predicate<KalahaPit> getGetPlayerHouse(){
        return getPlayerHouse;
    }

    public Predicate<KalahaPit> getGetPitByPosition(int position){
        this.position = position;
        return getPitByPosition;
    }



}
