package org.npeonelove.authservice.model;

import lombok.Data;

@Data
public class JwtAuthenticationDTO {
    private String token;
    private String refreshToken;
}
