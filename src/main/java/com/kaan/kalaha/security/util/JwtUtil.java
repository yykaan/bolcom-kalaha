package com.kaan.kalaha.security.util;


import com.kaan.kalaha.exception.JWTTokenException;
import com.kaan.kalaha.security.model.SecurityUser;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Optional;

/**
 * JWT token util class responsible for creating and validating JWT tokens.
 */
public class JwtUtil {

    public static String SECRET_KEY = "superSecret";

    /**
     * Creates a JWT token for the given user with the given expiration time and signs it with the secret key {@link #SECRET_KEY}.
     * @param user - user to generate token for
     * @return - generated token
     */
    public static String generateToken(SecurityUser user) {
        Date currentDate = new Date();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(currentDate)
                .setExpiration(null)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Validates the given token and returns the user if the token is valid
     * otherwise throws JWT token exception {@link JWTTokenException}.
     * @param token - token to validate
     * @return - body of the token
     */
    public static Optional<Claims> validateToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            return Optional.of(body);
        } catch (SignatureException e) {
            throw new JWTTokenException("Signature Exception");
        } catch (MalformedJwtException e) {
            throw new JWTTokenException("Token Exception");
        } catch (ExpiredJwtException e) {
            throw new JWTTokenException("Expired Token Exception");
        } catch (UnsupportedJwtException e) {
            throw new JWTTokenException("Unsupported Exception");
        }
    }
}
