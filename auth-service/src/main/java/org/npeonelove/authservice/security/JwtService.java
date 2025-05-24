package org.npeonelove.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.npeonelove.authservice.model.JwtAuthenticationDTO;
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

    public JwtAuthenticationDTO generateAuthToken(String email) {
        JwtAuthenticationDTO jwtDTO = new JwtAuthenticationDTO();
        jwtDTO.setToken(generateJwtToken(email));
        jwtDTO.setRefreshToken(generateRefreshToken(email));
        return jwtDTO;
    }

    // обновление jwt токена
    public JwtAuthenticationDTO refreshBaseToken(String email, String refreshToken) {
        JwtAuthenticationDTO jwtDTO = new JwtAuthenticationDTO();
        jwtDTO.setToken(generateJwtToken(email));
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

    // генерация jwt токена
    private String generateJwtToken(String email) {
        Date date = Date.from(LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .signWith(getSignKey())
                .compact();
    }

    // генерация refresh токена
    private String generateRefreshToken(String email) {
        Date date = Date.from(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .signWith(getSignKey())
                .compact();
    }

    // создание подписи
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
