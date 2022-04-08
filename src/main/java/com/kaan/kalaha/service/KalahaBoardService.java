package com.kaan.kalaha.service;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;

public interface KalahaBoardService {
    KalahaBoard createKalahaBoard(KalahaGame kalahaGame);

    KalahaBoard getKalahaBoardByGame(KalahaGame kalahaGame);

    void update(KalahaBoard kalahaBoard);
}
