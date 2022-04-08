package com.kaan.kalaha.service;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.enums.PitType;

public interface KalahaPitService {
    KalahaPit createPit(KalahaBoard kalahaBoard, PitType pitType, int position, int nrOfStones);

    KalahaPit updatePitNumberOfStones(KalahaBoard kalahaBoard, int position, int nrOfStones);

    KalahaPit updatePitNumberOfStonesByAmount(KalahaBoard kalahaBoard, int position, int amount);

    KalahaPit updatePitNumberOfStonesByOne(KalahaBoard kalahaBoard, int position);

    int getPitNumberOfStonesByBoardAndPosition(KalahaBoard kalahaBoard, int position);

    KalahaPit getPitByBoardAndPosition(KalahaBoard kalahaBoard, int position);

    void update(KalahaPit kalahaPit);
}
