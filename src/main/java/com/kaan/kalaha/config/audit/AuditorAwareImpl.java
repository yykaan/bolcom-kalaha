package com.kaan.kalaha.config.audit;

import com.kaan.kalaha.security.model.SecurityUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @NotNull
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            Object principal = authentication.getPrincipal();
            if (principal instanceof SecurityUser userDetails){
                return Optional.of(userDetails.getUsername());
            }
        }
        return Optional.of("system");
    }
}
