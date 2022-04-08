package com.kaan.kalaha.repository;

import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.enums.GameState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for @{@link KalahaGame}
 */
@Repository
public interface KalahaGameRepository extends CrudRepository<KalahaGame, Long> {
    /**
     * Find game by @{@link GameState}
     *
     * @param gameState @{@link GameState} to find by
     * @return List of @{@link KalahaGame} with given GameState
     */
    List<KalahaGame> findByGameState(GameState gameState);
}
