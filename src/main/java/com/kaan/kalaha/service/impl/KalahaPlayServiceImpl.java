package com.kaan.kalaha.service.impl;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.service.KalahaGameService;
import com.kaan.kalaha.service.KalahaPlayService;
import com.kaan.kalaha.service.KalahaPlayerService;
import com.kaan.kalaha.service.impl.rules.KalahaGameStartRule;
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


    @Override
    @Transactional
    public KalahaGame move(Long gameId, Long playerId, int position) {
        log.info("Finding KalahaGame with id: {}", gameId);
        KalahaGame kalahaGame = kalahaGameService.getGameById(gameId);

        log.info("Finding KalahaPlayer with id: {}", playerId);
        final KalahaPlayer kalahaPlayer = kalahaPlayerService.getPlayerById(playerId);

        kalahaGameStartRule.evaluate(kalahaGame, kalahaPlayer, position, null);
        return kalahaGameService.update(kalahaGame);
    }
}
