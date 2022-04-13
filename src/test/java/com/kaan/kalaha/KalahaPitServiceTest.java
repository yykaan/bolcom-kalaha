package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.repository.KalahaBoardRepository;
import com.kaan.kalaha.repository.KalahaPitRepository;
import com.kaan.kalaha.service.impl.KalahaPitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.kaan.kalaha.TestUtils.createBoard;
import static com.kaan.kalaha.constant.KalahaGameConstants.TOTAL_PIT_COUNT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KalahaPitServiceTest {

    @Mock
    KalahaPitRepository kalahaPitRepository;

    @Mock
    KalahaBoardRepository kalahaBoardRepository;

    @InjectMocks
    KalahaPitServiceImpl kalahaPitService;

    @Test
    public void createPitsForBoard_success(){

        KalahaBoard kalahaBoard = createBoard();

        when(kalahaPitRepository.save(any(KalahaPit.class)))
                .thenReturn(new KalahaPit());

        when(kalahaBoardRepository.save(any(KalahaBoard.class)))
                .thenReturn(kalahaBoard);

        List<KalahaPit> pits = kalahaPitService.createPits(kalahaBoard);
        assertThat(pits).hasSize(TOTAL_PIT_COUNT);
    }
}
