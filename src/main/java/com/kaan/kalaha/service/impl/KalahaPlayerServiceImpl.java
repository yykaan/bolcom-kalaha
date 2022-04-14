package com.kaan.kalaha.service.impl;

import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.repository.KalahaPlayerRepository;
import com.kaan.kalaha.service.KalahaPlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaPlayerServiceImpl implements KalahaPlayerService {
    private final KalahaPlayerRepository kalahaPlayerRepository;

    @Override
    @Transactional(readOnly = true)
    public KalahaPlayer findPlayerByUsername(String username) {
        log.info("Finding KalahaPlayer for username: {}", username);
        return kalahaPlayerRepository.findOneByUsername(username);
    }

    @Override
    @Transactional
    public void save(KalahaPlayer kalahaPlayer) {
        log.info("Saving KalahaPlayer: {}", kalahaPlayer);
        kalahaPlayerRepository.save(kalahaPlayer);
        log.info("KalahaPlayer saved: {}", kalahaPlayer);
    }

    @Override
    @Transactional(readOnly = true)
    public KalahaPlayer getPlayerById(Long playerId) {
        return kalahaPlayerRepository.findById(playerId).orElse(null);
    }
}
