package com.kaan.kalaha.service.impl;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.GameState;
import com.kaan.kalaha.exception.GameNotFoundException;
import com.kaan.kalaha.exception.UserNotLoggedInException;
import com.kaan.kalaha.repository.KalahaGameRepository;
import com.kaan.kalaha.service.AuthService;
import com.kaan.kalaha.service.KalahaBoardService;
import com.kaan.kalaha.service.KalahaGameService;
import com.kaan.kalaha.service.KalahaPitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaGameServiceImpl implements KalahaGameService {
    private final KalahaGameRepository kalahaGameRepository;
    private final KalahaBoardService kalahaBoardService;
    private final KalahaPitService kalahaPitService;
    private final AuthService authService;

    @Override
    @Transactional
    public KalahaGame createNewGame() {
        KalahaPlayer kalahaPlayer = authService.getCurrentUser();
        if (kalahaPlayer == null){
            throw new UserNotLoggedInException("User not logged in!");
        }
        log.info("Creating new KalahaGame for player: {}", kalahaPlayer);
        KalahaGame kalahaGame  = new KalahaGame(kalahaPlayer, kalahaPlayer, GameState.WAITING_FOR_OTHER_PLAYER);

        KalahaBoard board = kalahaBoardService.createKalahaBoard(kalahaGame);
        log.info("KalahaBoard {} created for KalahaGame {}", board, kalahaGame);

        kalahaPitService.createPits(board);
        log.info("KalahaPits created for KalahaBoard {}", board);

        kalahaGame.setKalahaBoard(board);

        kalahaGameRepository.save(kalahaGame);
        log.info("Created new KalahaGame {} for player: {}", kalahaGame, kalahaPlayer);

        return kalahaGame;
    }

    @Override
    @Transactional
    public KalahaGame update(KalahaGame kalahaGame) {
        if(kalahaGameRepository.existsById(kalahaGame.getId())){
            return kalahaGameRepository.save(kalahaGame);
        }
        throw new GameNotFoundException("Game not found with id:"+kalahaGame.getId());
    }

    @Override
    @Transactional
    public KalahaGame joinGame(KalahaPlayer player, Long gameId) {
        log.info("Joining KalahaGame with id: {} for player: {}", gameId, player.toString());
        KalahaGame kalahaGame = getGameById(gameId);
        log.info("KalahaGame found, joining the game: {}", kalahaGame.toString());


        if (player.equals(kalahaGame.getFirstPlayer()) || player.equals(kalahaGame.getSecondPlayer())){
            return kalahaGame;
        }
        // Set Second player
        log.info("Setting second player: {} for KalahaGame {}", player, kalahaGame);
        kalahaGame.setSecondPlayer(player);
        log.info("Second player set: {}", player);

        // Update gamestate
        log.info("Updating KalahaGame {} gamestate to: {}", kalahaGame, GameState.IN_PROGRESS);
        updateGameState(kalahaGame, GameState.IN_PROGRESS);
        log.info("KalahaGame updated: {}", kalahaGame);

        // Save KalahaGame
        log.info("Saving KalahaGame: {}", kalahaGame);
        kalahaGameRepository.save(kalahaGame);
        log.info("KalahaGame saved: {}", kalahaGame);

        return kalahaGame;
    }

    @Override
    @Transactional
    public KalahaGame updateGameState(KalahaGame game, GameState gameState) {
        log.info("Updating KalahaGame gamestate to: {}", gameState);
        KalahaGame kalahaGame = getGameById(game.getId());

        log.info("KalahaGame found: {}", kalahaGame.toString());
        kalahaGame.setGameState(gameState);
        log.info("KalahaGame updated: {}", kalahaGame);

        log.info("Saving KalahaGame for updating gamestate: {}", kalahaGame);
        kalahaGameRepository.save(kalahaGame);
        log.info("KalahaGame saved for updating gamestate: {}", kalahaGame);

        return kalahaGame;
    }

    @Override
    @Transactional(readOnly = true)
    public KalahaGame getGameById(Long id) {
        Optional<KalahaGame> kalahaGame = kalahaGameRepository.findById(id);
        if (kalahaGame.isEmpty()) {
            throw new GameNotFoundException("Game not found with id"+id);
        }else {
            return kalahaGame.get();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<KalahaGame> getGamesToJoin(KalahaPlayer player) {
        log.info("Getting KalahaGames available to join for player: {}", player.toString());
        return kalahaGameRepository.findByGameState(GameState.WAITING_FOR_OTHER_PLAYER)
                .stream().filter(
                        kalahaGame -> kalahaGame.getFirstPlayer() != player
                ).collect(Collectors.toList());
    }
}
