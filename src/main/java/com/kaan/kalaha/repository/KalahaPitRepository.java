package com.kaan.kalaha.repository;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaPit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link KalahaPit}
 */
@Repository
public interface KalahaPitRepository extends CrudRepository<KalahaPit, Long> {
    /**
     * Find Pit by {@link KalahaBoard}
     *
     * @param KalahaBoard {@link KalahaBoard} to find pits by
     * @return List of {@link KalahaPit}
     */
    List<KalahaPit> findByKalahaBoardOrderByPositionAsc(KalahaBoard KalahaBoard);

    /**
     * Find Pit by {@link KalahaBoard} and position
     *
     * @param KalahaBoard {@link KalahaBoard} to find by
     * @param position to find by
     * @return {@link KalahaPit} by KalahaBoard and position
     */
    KalahaPit findByKalahaBoardAndPosition(KalahaBoard KalahaBoard, int position);
}
