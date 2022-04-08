package com.kaan.kalaha.service;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPlayer;

public interface KalahaRule {
    KalahaGame evaluate(KalahaGame kahalaGame, KalahaPlayer playerId, int position);
    KalahaRule getNextRule();
}
