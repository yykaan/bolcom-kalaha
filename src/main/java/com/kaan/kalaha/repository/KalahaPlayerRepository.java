package com.kaan.kalaha.repository;

import com.kaan.kalaha.entity.KalahaPlayer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link KalahaPlayer}
 */
@Repository
public interface KalahaPlayerRepository extends CrudRepository<KalahaPlayer, Long> {
    /**
     * Find Player by username
     *
     * @param username of the player
     * @return {@link KalahaPlayer} by name
     */
    KalahaPlayer findOneByUsername(String username);
}
