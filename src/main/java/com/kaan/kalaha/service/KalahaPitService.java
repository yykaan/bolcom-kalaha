package com.kaan.kalaha.service;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaPit;

import java.util.List;

public interface KalahaPitService {
    List<KalahaPit> createPits(KalahaBoard kalahaBoard);
}
