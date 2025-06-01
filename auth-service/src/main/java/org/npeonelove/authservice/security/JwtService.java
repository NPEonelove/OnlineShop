package org.npeonelove.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.npeonelove.authservice.dto.jwt.JwtAuthenticationDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtService {

    @Value("${jwt.secret-key}")
    private String jwtSecret;

    public JwtAuthenticationDTO generateAuthToken(String email, String role) {
        JwtAuthenticationDTO jwtDTO = new JwtAuthenticationDTO();
        jwtDTO.setToken(generateJwtToken(email, role));
        jwtDTO.setRefreshToken(generateRefreshToken(email, role));
        return jwtDTO;
    }

    // обновление jwt токена
    public JwtAuthenticationDTO refreshBaseToken(String email, String role, String refreshToken) {
        JwtAuthenticationDTO jwtDTO = new JwtAuthenticationDTO();
        jwtDTO.setToken(generateJwtToken(email, role));
        jwtDTO.setRefreshToken(refreshToken);
        return jwtDTO;
    }

    // получаем email из токена
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    // проверка токена
    public boolean validateToken(String token) {
        try {
            Jwts.parser().
                    verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (Exception e) {
            System.out.println("Invalid JWT");
        }

        return false;
    }

    // генерация base токена
    private String generateJwtToken(String email, String role) {
        Date expiration = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return generateToken(email, role, expiration);
    }

    // генерация refresh токена
    private String generateRefreshToken(String email, String role) {
        Date expiration = Date.from(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant());
        return generateToken(email, role, expiration);
    }

    private String generateToken(String email, String role, Date expiration) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .expiration(expiration)
                .signWith(getSignKey())
                .compact();
    }

    // создание подписи
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
