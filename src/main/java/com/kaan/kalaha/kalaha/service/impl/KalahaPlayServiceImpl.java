package com.kaan.kalaha.kalaha.service.impl;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.exception.GameNotFoundException;
import com.kaan.kalaha.gameRules.KalahaGameStartRule;
import com.kaan.kalaha.service.AuthService;
import com.kaan.kalaha.service.KalahaGameService;
import com.kaan.kalaha.service.KalahaPlayService;
import com.kaan.kalaha.service.KalahaPlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaPlayServiceImpl implements KalahaPlayService {

    private final KalahaGameService kalahaGameService;
    private final KalahaPlayerService kalahaPlayerService;
    private final KalahaGameStartRule kalahaGameStartRule;
    private final AuthService authService;


    @Override
    @Transactional
    public KalahaGame move(Long gameId, int position) throws RuntimeException{
        log.info("Finding KalahaGame with id: {}", gameId);
        KalahaGame kalahaGame = kalahaGameService.getGameById(gameId);
        if (kalahaGame == null){
            throw new GameNotFoundException("Game not found!");
        }
        log.info("KalahaGame found with id: {}", gameId);

        log.info("Finding KalahaPlayer for KalahaGame with id: {}", kalahaGame);
        final KalahaPlayer kalahaPlayer = kalahaPlayerService.getPlayerById(authService.getCurrentUser().getId());
        log.info("Found KalahaPlayer {} for KalahaGame with id: {}", kalahaPlayer, kalahaGame);

        log.info("KalahaGame evaluation started for KalahaGame: {}", kalahaGame);
        kalahaGameStartRule.evaluate(kalahaGame, kalahaPlayer, position, null);
        log.info("KalahaGameStartRule evaluation ended for KalahaGame: {}", kalahaGame);
        return kalahaGameService.update(kalahaGame);
    }
}
