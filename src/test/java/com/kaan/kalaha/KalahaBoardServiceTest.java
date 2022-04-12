package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaBoard;
import com.kaan.kalaha.entity.KalahaGame;
import com.kaan.kalaha.repository.KalahaBoardRepository;
import com.kaan.kalaha.service.impl.KalahaBoardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KalahaBoardServiceTest {

    @Mock
    KalahaBoardRepository kalahaBoardRepository;

    @InjectMocks
    KalahaBoardServiceImpl kalahaBoardService;

    @Test
    public void createKalahaBoard(){
        when(kalahaBoardRepository.save(any(KalahaBoard.class)))
                .thenReturn(new KalahaBoard());

        KalahaBoard kalahaBoard = kalahaBoardService.createKalahaBoard(new KalahaGame());

        assertThat(kalahaBoard).isNotNull();
    }
}
