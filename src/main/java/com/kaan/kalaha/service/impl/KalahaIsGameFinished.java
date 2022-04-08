package com.kaan.kalaha.service.impl;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.service.KalahaRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaIsGameFinished implements KalahaRule {
    @Override
    public KalahaGame evaluate(KalahaGame kahalaGame, KalahaPlayer playerId, int position) {
        return null;
    }

    @Override
    public KalahaRule getNextRule() {
        return null;
    }
}
