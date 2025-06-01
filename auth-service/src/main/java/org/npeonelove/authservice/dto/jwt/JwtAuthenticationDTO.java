package org.npeonelove.authservice.dto.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Ответ с JWT токенами аутентификации")
public class JwtAuthenticationDTO {

    @Schema(description = "Access токен для авторизации запросов", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Refresh токен для обновления access токена", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;
}
