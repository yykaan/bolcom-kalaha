package com.kaan.kalaha.service.impl;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.enums.PitType;
import com.kaan.kalaha.repository.KalahaPitRepository;
import com.kaan.kalaha.service.KalahaPitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaPitServiceImpl implements KalahaPitService {
    private final KalahaPitRepository kalahaPitRepository;

    /**
     * Function to create a KalahaPit
     *
     * @param kalahaBoard @{@link KalahaBoard} to create on
     * @param pitType @{@link PitType} of the KalahaPit to create
     * @param position of the KalahaPit
     * @param nrOfStones number of stones in the KalahaPit
     * @return @{@link KalahaPit} created
     */
    @Override
    @Transactional
    public KalahaPit createPit(KalahaBoard kalahaBoard, PitType pitType, int position, int nrOfStones) {
        log.info("Creating KalahaPit on KalahaBoard {}", kalahaBoard.getId());
        KalahaPit kalahaPit = new KalahaPit(kalahaBoard, position, nrOfStones, pitType);

        log.info("Saving KalahaPit {}", kalahaPit);
        kalahaPitRepository.save(kalahaPit);
        log.info("KalahaPit saved {}", kalahaPit);

        return kalahaPit;
    }

    /**
     * Function to update the number of stones on a KalahaPit
     *
     * @param kalahaBoard @{@link KalahaBoard} the KalahaPit is on
     * @param position of the KalahaPit
     * @param nrOfStones new number of stones in the KalahaPit
     * @return @{@link KalahaPit} that was updated
     */
    @Override
    @Transactional
    public KalahaPit updatePitNumberOfStones(KalahaBoard kalahaBoard, int position, int nrOfStones) {
        log.info("Updating KalahaPit on KalahaBoard {}", kalahaBoard.getId());
        KalahaPit kalahaPit = getPitByBoardAndPosition(kalahaBoard, position);
        log.info("KalahaPit retrieved {}", kalahaPit);

        log.info("Updating KalahaPit {}", kalahaPit);
        kalahaPit.setStones(nrOfStones);

        log.info("Saving KalahaPit {}", kalahaPit);
        kalahaPitRepository.save(kalahaPit);
        log.info("KalahaPit saved {}", kalahaPit);

        return kalahaPit;
    }

    /**
     * Function to update the number of stones on a KalahaPit by a certain amount
     *
     * @param kalahaBoard @{@link KalahaBoard} the KalahaPit is on
     * @param position of the KalahaPit
     * @param amount number of stones to add in the KalahaPit
     * @return @{@link KalahaPit} that was updated
     */
    @Override
    @Transactional
    public KalahaPit updatePitNumberOfStonesByAmount(KalahaBoard kalahaBoard, int position, int amount) {
        log.info("Updating KalahaPit on KalahaBoard {}", kalahaBoard.getId());
        KalahaPit kalahaPit = getPitByBoardAndPosition(kalahaBoard, position);
        log.info("KalahaPit retrieved {}", kalahaPit);

        log.info("Updating KalahaPit {}", kalahaPit);
        int currentAmount = kalahaPit.getStones();
        int newAmount = currentAmount + amount;
        kalahaPit.setStones(newAmount);

        kalahaPitRepository.save(kalahaPit);
        log.info("KalahaPit updated {}", kalahaPit);

        return kalahaPit;
    }

    /**
     * Function to update the number of stones on a KalahaPit by one
     *
     * @param kalahaBoard @{@link KalahaBoard} the KalahaPit is on
     * @param position of the KalahaPit
     * @return @{@link KalahaPit} that was updated
     */
    @Override
    @Transactional
    public KalahaPit updatePitNumberOfStonesByOne(KalahaBoard kalahaBoard, int position) {
        log.info("Updating KalahaPit on KalahaBoard {}", kalahaBoard.getId());
        KalahaPit kalahaPit = getPitByBoardAndPosition(kalahaBoard, position);

        log.info("Updating KalahaPit {}", kalahaPit);
        int currentAmount = kalahaPit.getStones();
        log.info("Current amount of stones {} for board {}", currentAmount, kalahaBoard);
        currentAmount++;
        log.info("Current amount of stones updated to {} for board {}", currentAmount, kalahaBoard);
        kalahaPit.setStones(currentAmount);

        log.info("Saving KalahaPit {}", kalahaPit);
        kalahaPitRepository.save(kalahaPit);
        log.info("KalahaPit saved {}", kalahaPit);

        return kalahaPit;
    }

    /**
     * Function to get the number of stones by KalahaBoard and position
     *
     * @param kalahaBoard @{@link KalahaBoard} to get info from
     * @param position of the KalahaPit to get info from
     * @return The number of stones on the KalahaPit
     */
    @Override
    @Transactional
    public int getPitNumberOfStonesByBoardAndPosition(KalahaBoard kalahaBoard, int position) {
        log.info("Getting number of stones on KalahaPit on KalahaBoard {}", kalahaBoard.getId());
        KalahaPit kalahaPit = getPitByBoardAndPosition(kalahaBoard, position);
        log.info("KalahaPit retrieved {}", kalahaPit);
        return kalahaPit.getStones();
    }

    /**
     * Function to retrieve a KalahaPit by @{@link KalahaBoard} and position
     *
     * @param kalahaBoard @{@link KalahaBoard} to get KalahaPit from
     * @param position of the KalahaPit
     * @return @{@link KalahaPit} matching params
     */
    @Override
    @Transactional(readOnly = true)
    public KalahaPit getPitByBoardAndPosition(KalahaBoard kalahaBoard, int position) {
        log.info("Getting KalahaPit on KalahaBoard {}", kalahaBoard.getId());
        return kalahaPitRepository.findByKalahaBoardAndPosition(kalahaBoard, position);
    }

    @Override
    public void update(KalahaPit kalahaPit) {
        log.info("Updating KalahaPit {}", kalahaPit);
        kalahaPitRepository.save(kalahaPit);
    }
}
