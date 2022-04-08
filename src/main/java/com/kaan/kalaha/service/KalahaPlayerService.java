package com.kaan.kalaha.service;

import com.kaan.kalaha.entity.KalahaPlayer;

public interface KalahaPlayerService {
    KalahaPlayer findPlayerByUsername(String username);

    void save(KalahaPlayer kalahaPlayer);

    KalahaPlayer getPlayerById(Long playerId);
}
