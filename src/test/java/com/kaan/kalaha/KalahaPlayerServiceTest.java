package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.repository.KalahaPlayerRepository;
import com.kaan.kalaha.service.impl.KalahaPlayerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KalahaPlayerServiceTest {

    @Mock
    KalahaPlayerRepository kalahaPlayerRepository;

    @InjectMocks
    KalahaPlayerServiceImpl kalahaPlayerService;

    @Test
    public void findPlayerByUsername_success(){
        when(kalahaPlayerRepository.findOneByUsername(anyString()))
                .thenReturn(createKalahaPlayer());

        KalahaPlayer playerByUsername = kalahaPlayerService.findPlayerByUsername("bolcomtest");

        assertThat(playerByUsername).isEqualTo(createKalahaPlayer());
    }

    @Test
    public void save_success(){
        when(kalahaPlayerRepository.save(any(KalahaPlayer.class)))
                .thenReturn(createKalahaPlayer());

        kalahaPlayerService.save(createKalahaPlayer());

    }

    @Test
    public void getPlayerById_success(){
        when(kalahaPlayerRepository.findById(anyLong()))
                .thenReturn(Optional.of(createKalahaPlayer()));

        KalahaPlayer playerById = kalahaPlayerService.getPlayerById(1L);
        assertThat(playerById).isEqualTo(createKalahaPlayer());

    }

    @Test
    public void getPlayerById_success_butPlayerNotFound(){
        when(kalahaPlayerRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        KalahaPlayer playerById = kalahaPlayerService.getPlayerById(1L);
        assertThat(playerById).isNull();

    }

    private KalahaPlayer createKalahaPlayer(){
        return new KalahaPlayer("bolcomtest","a@bol.com","strongpassword");
    }
}
