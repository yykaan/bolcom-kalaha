package com.kaan.kalaha;

import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.exception.GameNotFoundException;
import com.kaan.kalaha.repository.KalahaPlayerRepository;
import com.kaan.kalaha.security.model.SecurityUser;
import com.kaan.kalaha.security.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.kaan.kalaha.TestUtils.createKalahaPlayer;
import static com.kaan.kalaha.TestUtils.createKalahaPlayer2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    @Mock
    KalahaPlayerRepository kalahaPlayerRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    public void loadUserByUsernameTest_success() {
        when(kalahaPlayerRepository.findOneByUsername(createKalahaPlayer().getUsername()))
                .thenReturn(createKalahaPlayer());

        SecurityUser securityUser = userDetailsService.loadUserByUsername(createKalahaPlayer().getUsername());

        assertThat(securityUser.getUsername()).isEqualTo(createKalahaPlayer().getUsername());
    }

    @Test
    public void loadUserByUsernameTest_fail_throwsUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(null));
    }

    @Test
    public void loadUserByUsernameTest_fail_userNotFoundOnDb_throwsUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(createKalahaPlayer2().getUsername()));
    }

}
