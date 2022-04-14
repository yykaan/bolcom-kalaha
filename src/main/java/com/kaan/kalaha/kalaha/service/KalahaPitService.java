package com.kaan.kalaha.kalaha.service;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaPit;

import java.util.List;

/**
 * Responsible for {@link KalahaPit} related operations.
 */
public interface KalahaPitService {
    /**
     * Creates a new {@link KalahaPit} and adds it to the {@link KalahaBoard}.
     * @param kalahaBoard {@link KalahaBoard}
     * @return {@link List} of {@link KalahaPit}
     */
    List<KalahaPit> createPits(KalahaBoard kalahaBoard);
}
