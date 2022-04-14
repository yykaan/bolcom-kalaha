package com.kaan.kalaha.kalaha.security.service;

import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.repository.KalahaPlayerRepository;
import com.kaan.kalaha.security.model.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class for User details that is used for authentication
 * uses {@link KalahaPlayerRepository} to get user details
 */
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final KalahaPlayerRepository playerRepository;

    /**
     * @param username username of the user
     * @return {@link SecurityUser} with given username if exists
     * @throws UsernameNotFoundException if user is not found
     */
    @Override
    @Transactional(readOnly = true)
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        if(StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("Username cannot be empty");
        }

        KalahaPlayer player = playerRepository.findOneByUsername(username);
        if (player == null) {
            throw new UsernameNotFoundException("Player " + username + " doesn't exists");
        }
        return new SecurityUser(player.getUsername());
    }
}
