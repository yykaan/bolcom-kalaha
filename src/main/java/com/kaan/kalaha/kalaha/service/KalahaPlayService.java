package com.kaan.kalaha.kalaha.service;

import com.kaan.kalaha.entity.KalahaGame;

/**
 * Responsible for making a move in the game.
 */
public interface KalahaPlayService {
    KalahaGame move(Long gameId, int position);
}
