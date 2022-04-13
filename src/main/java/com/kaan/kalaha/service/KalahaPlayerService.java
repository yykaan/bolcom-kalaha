package com.kaan.kalaha.service;

import com.kaan.kalaha.entity.KalahaPlayer;

/**
 * Responsible for {@link KalahaPlayer} related operations
 */
public interface KalahaPlayerService {
    /**
     * Finds the player by the given name
     * @param username of the player
     * @return {@link KalahaPlayer}
     */
    KalahaPlayer findPlayerByUsername(String username);

    /**
     * Saves the given {@link KalahaPlayer}
     * @param kalahaPlayer to be saved
     */
    void save(KalahaPlayer kalahaPlayer);

    /**
     * Returns the {@link KalahaPlayer} by the given id
     * @param playerId of the player
     * @return {@link KalahaPlayer}
     */
    KalahaPlayer getPlayerById(Long playerId);
}
