package com.kaan.kalaha.service.impl;

import com.google.common.collect.Iterables;
import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.GameState;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.kaan.kalaha.constant.KalahaGameConstants.P1_STORE;
import static com.kaan.kalaha.constant.KalahaGameConstants.P2_STORE;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaPlayServiceImpl implements KalahaPlayService {

    private final KalahaGameService kalahaGameService;
    private final KalahaBoardService kalahaBoardService;
    private final KalahaPlayerService kalahaPlayerService;
    private final KalahaGameHelper kalahaGameHelper;
    private final KalahaStartingPitStoneCheck kalahaStartingPitStoneCheck;


    @Override
    @Transactional
    public KalahaBoard move(Long gameId, Long playerId, int position) {
        log.info("Finding KalahaGame with id: {}", gameId);
        KalahaGame kalahaGame = kalahaGameService.getGameById(gameId);

        log.info("Finding KalahaPlayer with id: {}", playerId);
        KalahaBoard kalahaBoard = kalahaGame.getKalahaBoard();

        log.info("Finding KalahaPlayer with id: {}", playerId);
        KalahaPlayer kalahaPlayer = kalahaPlayerService.getPlayerById(playerId);

        kalahaStartingPitStoneCheck.evaluate(kalahaGame, kalahaPlayer, position, null);

        log.info("Check if player turn {}", kalahaPlayer);
        if(isPlayerTurn(kalahaGame, kalahaPlayer) && kalahaGame.getGameState() != GameState.FINISHED) {
            log.info("Checking if player is P1 or P2 {}", kalahaPlayer);
            PlayerTurn playerTurn;
            if(kalahaPlayer == kalahaGame.getFirstPlayer()) {
                log.info("Player {} is P1 for game {}", kalahaPlayer, kalahaGame);
                playerTurn = PlayerTurn.P1;
            }
            else {
                log.info("Player {} is P2 for game {}", kalahaPlayer, kalahaGame);
                playerTurn = PlayerTurn.P2;
            }
            kalahaBoard = handleMove(kalahaGame, playerTurn, position);
        }
        log.info("Returning KalahaBoard {} and did nothing. Since it is not player turn and game is finished!", kalahaBoard);
        return kalahaBoard;
    }

    private KalahaBoard handleMove(KalahaGame kalahaGame, PlayerTurn playerTurn, int position) {

        log.info("Finding KalahaBoard for game {}", kalahaGame);
        KalahaBoard kalahaBoard = kalahaGame.getKalahaBoard();

        log.info("Finding KalahaPit for position {}", position);
        int stones = kalahaBoard.getPits().stream()
                .filter(kalahaGameHelper.getGetPitByPosition(position))
                .findFirst()
                .get()
                .getStones();
        log.info("Found {} stones for position {}", stones, position);

        log.info("Checking if stones are greater than 0 {}", stones);
        if(kalahaGameHelper.validateStartingPitPositionByPlayerTurn(playerTurn, position, stones)) {
            log.info("Stones are greater than 0 {}", stones);
            int index = sowStones(kalahaGame,
                    position,
                    playerTurn);

            log.info("Checking if opposite pit stones are capturable {}", index);
            checkCapture(kalahaBoard,
                    index,
                    playerTurn);

            if (index != kalahaGameHelper.getPlayerStoreIndexByPlayerTurn(playerTurn)) {
                kalahaGameService.switchTurn(kalahaGame.getSecondPlayer(), kalahaGame.getId());
            }

            boolean isFinished = checkFinished(kalahaBoard, playerTurn);

            if(isFinished) {
                emptyAllPits(kalahaBoard);
                kalahaGameService.updateGameState(kalahaGame, GameState.FINISHED);
            }
        }

        return kalahaBoard;
    }

    private boolean checkFinished(KalahaBoard kalahaBoard, PlayerTurn playerTurn) {
        log.info("Checking if game is finished");
        return kalahaBoard.getPits().stream()
                .filter(kalahaGameHelper.getGetPlayerPits(playerTurn))
                .filter(kalahaGameHelper.getGetPlayerOnlyPits())
                .noneMatch(kalahaGameHelper.getIsPitHasStone());
    }

    private int sowStones(KalahaGame kalahaGame, int position, PlayerTurn playerTurn) {
        log.info("Sowing stones from KalahaGame {}, starting position {}", kalahaGame, position);
        if (position == P1_STORE || position == P2_STORE) {
            throw new RuntimeException("Cannot sow stones from store");
        }

        log.info("Deciding whose turn it is {}", kalahaGame.getPlayerTurn());
        if (kalahaGame.getPlayerTurn() == null){
            if (position < P1_STORE) {
                kalahaGameService.switchTurn(kalahaGame.getFirstPlayer(), kalahaGame.getId());
            }else {
                kalahaGameService.switchTurn(kalahaGame.getSecondPlayer(), kalahaGame.getId());
            }
        }
        log.info("KalahaGame {}, turn for {}", kalahaGame, kalahaGame.getPlayerTurn());

        if (kalahaGame.getPlayerTurn() == kalahaGame.getFirstPlayer() && position > P1_STORE
        || kalahaGame.getPlayerTurn() == kalahaGame.getSecondPlayer() && position < P1_STORE) {
            throw new RuntimeException("Wrong turn");
        }

        log.info("Getting Starting KalahaPit for KalahaGame {}", kalahaGame);
        KalahaBoard kalahaBoard = kalahaGame.getKalahaBoard();
        KalahaPit startingKalahaPit = Iterables.getOnlyElement(kalahaBoard.getPits().stream()
                .filter(kalahaGameHelper.getGetPitByPosition(position))
                .collect(Collectors.toList()));

        int stones = startingKalahaPit.getStones();

        if (stones == 0) {
            throw new RuntimeException("No stones to sow");
        }

        int pitIndex = position + 1;
        startingKalahaPit.setStones(0);

        log.info("Sowing stones from KalahaPit {}", startingKalahaPit);
        while (stones > 0) {
            if (PlayerTurn.P1.equals(playerTurn)){
                if (pitIndex == P2_STORE){
                    pitIndex++;
                    continue;
                }
            }else {
                if (pitIndex == P1_STORE){
                    pitIndex++;
                    continue;
                }
            }

            KalahaPit kalahaPit = Iterables.getOnlyElement(kalahaBoard.getPits().stream()
                    .filter(kalahaGameHelper.getGetPitByPosition(pitIndex))
                    .collect(Collectors.toList()));
            kalahaPit.setStones(kalahaPit.getStones() + 1);
            log.info("KalahaGame {} ,sowed stones to KalahaPit {}", kalahaGame, kalahaPit);

            pitIndex++;
            stones--;
        }

        return pitIndex;
    }

    private void checkCapture(KalahaBoard kalahaBoard, int index, PlayerTurn playerTurn) {
        log.info("Checking if stones can be captured from opponent for KalahaBoard {}", kalahaBoard);
        if (index >= kalahaGameHelper.getLowerPositionByPlayerTurn(playerTurn) &&
                index <= kalahaGameHelper.getUpperPositionByPlayerTurn(playerTurn) &&
                    kalahaBoard.getPits().stream().filter(kalahaGameHelper.getGetPitByPosition(index)).findFirst().get().getStones() == 1) {
            Integer oppositePitStones = kalahaBoard.getPits().stream()
                    .filter(kalahaGameHelper.getGetPitByPosition(kalahaGameHelper.getOppositePitIndex(index)))
                    .findFirst().get().getStones();

            if (oppositePitStones > 0) {
                kalahaBoard.getPits().stream()
                        .filter(kalahaGameHelper.getGetPitByPosition(kalahaGameHelper.getOppositePitIndex(index)))
                        .findFirst()
                        .ifPresentOrElse(kalahaPit -> {
                           kalahaPit.setStones(0);
                        },() -> {
                            log.info("Opposite pit is empty");
                        });

                kalahaBoard.getPits().stream()
                        .filter(kalahaGameHelper.getGetPitByPosition(index))
                        .findFirst()
                        .ifPresentOrElse(kalahaPit -> {
                            kalahaPit.setStones(0);
                        },() -> {
                            log.info("Pit is empty");
                        });

                kalahaBoard.getPits().stream()
                        .filter(kalahaGameHelper.getGetPlayerHouse())
                        .findFirst()
                        .ifPresentOrElse(kalahaPit -> {
                            kalahaPit.setStones(kalahaPit.getStones() + oppositePitStones + 1);
                        },() -> {
                            log.info("Player house is empty");
                        });
            }
        }
    }

    private void emptyAllPits(KalahaBoard board) {
        Arrays.stream(PlayerTurn.values()).forEach(playerTurn -> {
            emptyPlayerPitsByPlayerTurnAndPutStonesOnPlayerHouse(board, playerTurn);
                });
    }

    private void emptyPlayerPitsByPlayerTurnAndPutStonesOnPlayerHouse(KalahaBoard kalahaBoard, PlayerTurn playerTurn) {
        log.info("Emptying player pits by player turn {}", playerTurn);
        KalahaPit playerHouse = kalahaBoard.getPits().stream()
                .filter(kalahaGameHelper.getGetPlayerPits(playerTurn))
                .filter(kalahaGameHelper.getGetPlayerHouse()).toList().get(0);

        kalahaBoard.getPits().stream()
                .filter(kalahaGameHelper.getGetPlayerPits(playerTurn))
                .filter(kalahaGameHelper.getGetPlayerOnlyPits())
                .forEach(kalahaPit -> {
                    int totalStone = 0;
                    totalStone = totalStone + kalahaPit.getStones();
                    kalahaPit.setStones(0);
                    playerHouse.setStones(playerHouse.getStones() + totalStone);
                });
        kalahaBoardService.update(kalahaBoard);
        log.info("Player pits by player turn {} are empty", playerTurn);
    }

    private boolean isPlayerTurn(KalahaGame kalahaGame, KalahaPlayer kalahaPlayer) {
        log.info("Checking if player turn {}", kalahaPlayer);
        boolean isPlayerTurn =  kalahaGame.getPlayerTurn().equals(kalahaPlayer);
        log.info("Player {} is in turn {}", kalahaPlayer, isPlayerTurn);
        return isPlayerTurn;
    }
}
