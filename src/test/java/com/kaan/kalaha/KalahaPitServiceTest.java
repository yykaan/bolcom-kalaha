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

import static com.kaan.kalaha.constant.KalahaGameConstants.TOTAL_PIT_COUNT;
import static org.assertj.core.api.Assertions.assertThat;

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
        List<KalahaPit> pits = kalahaPitService.createPits(new KalahaBoard());
        assertThat(pits).hasSize(TOTAL_PIT_COUNT);
    }
}
