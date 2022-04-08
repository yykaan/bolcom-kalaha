package com.kaan.kalaha.security.filter;

import com.kaan.kalaha.config.cache.CacheManager;
import com.kaan.kalaha.entity.KalahaPlayer;
import com.kaan.kalaha.exception.JWTExceptionResponse;
import com.kaan.kalaha.exception.JWTTokenException;
import com.kaan.kalaha.security.model.SecurityUser;
import com.kaan.kalaha.security.util.JwtUtil;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtFilter extends OncePerRequestFilter {

    private static final String BEARER_ = "Bearer ";
    private static final String HEADER_NAME = "Authorization";
    private final CacheManager cacheManager;

    public JwtFilter(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Optional.ofNullable(request.getHeader(HEADER_NAME))
                    .filter(s -> s.startsWith(BEARER_))
                    .map(s -> s.split(BEARER_)[1])
                    .map(key -> {
                        if (StringUtils.isNotEmpty(key)) {
                            String value = cacheManager.getValue(key);
                            if (StringUtils.isEmpty(value)) {
                                throw new JWTTokenException("Token is not found");
                            }
                            return value;
                        }
                        return key;
                    })
                    .flatMap(JwtUtil::validateToken)
                    .ifPresent(claims -> {
                        SecurityUser user = new SecurityUser(claims.getSubject());
                        final UserDetails userDetails = user;

                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(userDetails, null,
                                        user.getAuthorities());

                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(auth);
                    });
            filterChain.doFilter(request, response);
        } catch (JWTTokenException exception) {
            JWTExceptionResponse responseDto = new JWTExceptionResponse();
            responseDto.setStatus(HttpStatus.UNAUTHORIZED.value());
            responseDto.setMessage("An error occurred");
            responseDto.setTokenAvailable(false);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }

    }
}
