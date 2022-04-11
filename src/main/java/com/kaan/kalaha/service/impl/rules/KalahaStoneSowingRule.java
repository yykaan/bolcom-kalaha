package com.kaan.kalaha.service.impl.rules;

import com.google.common.collect.Iterables;
import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.enums.PlayerTurn;
import com.kaan.kalaha.service.KalahaRule;
import com.kaan.kalaha.service.impl.KalahaGameHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.kaan.kalaha.constant.KalahaGameConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaStoneSowingRule implements KalahaRule {
    private final KalahaGameHelper kalahaGameHelper;
    private final KalahaCaptureRule kalahaCaptureRule;

    @Override
    public KalahaGame evaluate(KalahaGame kalahaGame, KalahaPlayer player, int position, PlayerTurn playerTurn) {
        log.info("Stone sowing rule is applied");
        KalahaBoard kalahaBoard = kalahaGame.getKalahaBoard();
        KalahaPit startingKalahaPit = Iterables.getOnlyElement(kalahaBoard.getPits().stream()
                .filter(kalahaGameHelper.getGetPitByPosition(position))
                .collect(Collectors.toList()));

        int stones = startingKalahaPit.getStones();

        if (stones == 0) {
            throw new RuntimeException("No stones to sow");
        }

        int pitIndex = position + 1;
        startingKalahaPit.setStones(0);

        log.info("Sowing stones from KalahaPit {}", startingKalahaPit);
        while (stones > 0) {
            if (PlayerTurn.P1.equals(playerTurn)){
                if (pitIndex == P2_STORE){
                    pitIndex++;
                    continue;
                }
            }else {
                if (pitIndex == P1_STORE){
                    pitIndex++;
                    continue;
                }
            }

            if (pitIndex > TOTAL_PIT_COUNT){
                pitIndex = P1_LOWER_BOUNDARY;
            }

            KalahaPit kalahaPit = Iterables.getOnlyElement(kalahaBoard.getPits().stream()
                    .filter(kalahaGameHelper.getGetPitByPosition(pitIndex))
                    .collect(Collectors.toList()));
            kalahaPit.setStones(kalahaPit.getStones() + 1);
            log.info("KalahaGame {} ,sowed stones to KalahaPit {}", kalahaGame, kalahaPit);

            pitIndex++;
            stones--;
        }
        log.info("Stone sowing rule is applied");
        getNextRule().evaluate(kalahaGame, player, pitIndex, playerTurn);

        return kalahaGame;
    }

    @Override
    public KalahaRule getNextRule() {
        return kalahaCaptureRule;
    }
}
