package com.kaan.kalaha.security.util;


import com.kaan.kalaha.context.ApplicationContextProvider;
import com.kaan.kalaha.exception.JWTTokenException;
import com.kaan.kalaha.security.model.SecurityUser;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Optional;


public class JwtUtil {

    public static String SECRET_KEY = ApplicationContextProvider.getProperty("jwt.secret");

    public static String generateToken(SecurityUser user) {
        Date currentDate = new Date();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(currentDate)
                .setExpiration(null)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

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