package com.kaan.kalaha.service;

import com.kaan.kalaha.entity.KalahaBoard;

public interface KalahaPlayService {

    KalahaBoard move(Long gameId, Long playerId, int position);
}
