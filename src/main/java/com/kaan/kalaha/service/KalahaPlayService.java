package com.kaan.kalaha.service;

import com.kaan.kalaha.entity.KalahaGame;

public interface KalahaPlayService {
    KalahaGame move(Long gameId, int position);
}
