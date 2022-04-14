package com.kaan.kalaha.service.impl;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.enums.PitType;
import com.kaan.kalaha.repository.KalahaBoardRepository;
import com.kaan.kalaha.repository.KalahaPitRepository;
import com.kaan.kalaha.service.KalahaPitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.kaan.kalaha.constant.KalahaGameConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaPitServiceImpl implements KalahaPitService {
    private final KalahaPitRepository kalahaPitRepository;
    private final KalahaBoardRepository kalahaBoardRepository;


    private KalahaPit createPit(KalahaBoard kalahaBoard, PitType pitType, int position, int nrOfStones) {
        log.info("Creating KalahaPit on KalahaBoard {}", kalahaBoard.getId());
        KalahaPit kalahaPit = new KalahaPit(kalahaBoard, position, nrOfStones, pitType);

        log.info("Saving KalahaPit {}", kalahaPit);
        kalahaPitRepository.save(kalahaPit);
        log.info("KalahaPit saved {}", kalahaPit);
        kalahaBoard.getPits().add(kalahaPit);
        kalahaBoardRepository.save(kalahaBoard);
        log.info("Created KalahaPit on KalahaBoard {}", kalahaBoard.getId());

        return kalahaPit;
    }

    @Override
    @Transactional
    public List<KalahaPit> createPits(KalahaBoard kalahaBoard){
        List<KalahaPit> pits = new ArrayList<>();
        pits.add(createPit(kalahaBoard, PitType.HOUSE, P1_STORE, HOUSE_STONE_INITIAL_COUNT));
        pits.add(createPit(kalahaBoard, PitType.HOUSE, P2_STORE, HOUSE_STONE_INITIAL_COUNT));

        // P1 houses
        for (int i = P1_LOWER_BOUNDARY; i <= P1_UPPER_BOUNDARY; i++) {
            pits.add(createPit(kalahaBoard, PitType.PIT, i, PIT_STONE_INITIAL_COUNT));
        }

        // P2 houses
        for (int i = P2_LOWER_BOUNDARY; i <= P2_UPPER_BOUNDARY; i++) {
            pits.add(createPit(kalahaBoard, PitType.PIT, i, PIT_STONE_INITIAL_COUNT));
        }
        return pits;
    }
}
