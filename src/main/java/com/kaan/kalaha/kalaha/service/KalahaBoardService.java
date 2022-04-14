package com.kaan.kalaha.kalaha.service;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;

/**
 * Responsible for {@link KalahaBoard} related logic
 */
public interface KalahaBoardService {
    /**
     * Creates a board for the given game
     * @param kalahaGame the kalaha game
     * @return the kalaha board {@link KalahaBoard}
     */
    KalahaBoard createKalahaBoard(KalahaGame kalahaGame);
}
