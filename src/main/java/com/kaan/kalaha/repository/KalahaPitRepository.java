package com.kaan.kalaha.repository;

import com.kaan.kalaha.entity.KalahaPit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link KalahaPit}
 */
@Repository
public interface KalahaPitRepository extends CrudRepository<KalahaPit, Long> {
}
