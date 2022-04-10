package com.kaan.kalaha.repository;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for @{@link KalahaBoard}
 */
@Repository
public interface KalahaBoardRepository extends CrudRepository<KalahaBoard, Long> {
    /**
     * Find a board by instance of @{@link KalahaGame}
     *
     * @param game @{@link KalahaGame} instance
     * @return @{@link KalahaBoard} instance
     */
    KalahaBoard findByGame(KalahaGame game);
}