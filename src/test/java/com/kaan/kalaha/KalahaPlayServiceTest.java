package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaPit;
import com.kaan.kalaha.repository.KalahaBoardRepository;
import com.kaan.kalaha.repository.KalahaPitRepository;
import com.kaan.kalaha.service.AuthService;
import com.kaan.kalaha.service.KalahaGameService;
import com.kaan.kalaha.service.KalahaPlayerService;
import com.kaan.kalaha.service.impl.KalahaPitServiceImpl;
import com.kaan.kalaha.service.impl.KalahaPlayServiceImpl;
import com.kaan.kalaha.service.impl.rules.KalahaGameStartRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.kaan.kalaha.constant.KalahaGameConstants.TOTAL_PIT_COUNT;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class KalahaPlayServiceTest {

    @Mock
    KalahaGameService kalahaGameService;

    @Mock
    KalahaPlayerService kalahaPlayerService;

    @Mock
    KalahaGameStartRule kalahaGameStartRule;

    @Mock
    AuthService authService;

    @InjectMocks
    KalahaPlayServiceImpl kalahaPlayService;

    @Test
    public void test(){
    }
}
