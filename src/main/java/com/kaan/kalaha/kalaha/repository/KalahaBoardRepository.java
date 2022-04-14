package com.kaan.kalaha.kalaha.repository;

import com.kaan.kalaha.entity.KalahaBoard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link KalahaBoard}
 */
@Repository
public interface KalahaBoardRepository extends CrudRepository<KalahaBoard, Long> {
}
