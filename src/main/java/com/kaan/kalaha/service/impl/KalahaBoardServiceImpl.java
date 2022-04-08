package com.kaan.kalaha.service.impl;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.repository.KalahaBoardRepository;
import com.kaan.kalaha.service.KalahaBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaBoardServiceImpl implements KalahaBoardService {
    private final KalahaBoardRepository kalahaBoardRepository;

    @Override
    @Transactional
    public KalahaBoard createKalahaBoard(KalahaGame kalahaGame) {
        log.info("Creating kalaha board for game {}", kalahaGame.toString());
        KalahaBoard kalahaBoard = new KalahaBoard(kalahaGame);
        return kalahaBoardRepository.save(kalahaBoard);
    }

    @Override
    @Transactional(readOnly = true)
    public KalahaBoard getKalahaBoardByGame(KalahaGame kalahaGame) {
        log.info("Getting kalaha board for game {}", kalahaGame.toString());
        return kalahaBoardRepository.findByGame(kalahaGame);
    }

    @Override
    public void update(KalahaBoard kalahaBoard) {
        log.info("Updating kalaha board {}", kalahaBoard.toString());
        kalahaBoardRepository.save(kalahaBoard);
    }
}
