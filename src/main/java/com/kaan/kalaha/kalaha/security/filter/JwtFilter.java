package com.kaan.kalaha.kalaha.security.filter;

import com.kaan.kalaha.config.cache.CacheManager;
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

/**
 * Filters every request only once to check if the request is authorized by JWT token
 * If the request is authorized, the user details are set to the SecurityContextHolder
 * If the request is not authorized, {@link JWTTokenException} is thrown
 *
 * Uses {@link JwtUtil} to validate the JWT token
 * Uses {@link CacheManager} to get the user details from the cache
 */
public class JwtFilter extends OncePerRequestFilter {

    private static final String BEARER_ = "Bearer ";
    private static final String HEADER_NAME = "Authorization";
    private final CacheManager cacheManager;

    public JwtFilter(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * Checks if request is authorized by JWT token
     * @param request the request
     * @param response the response
     * @param filterChain the filter chain
     * @throws ServletException
     * @throws IOException
     */
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
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }

    }
}
