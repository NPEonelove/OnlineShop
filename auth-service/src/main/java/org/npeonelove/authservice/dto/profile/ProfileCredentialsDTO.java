package org.npeonelove.authservice.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Учетные данные пользователя")
public class ProfileCredentialsDTO {
    @NotBlank(message = "Email обязателен для заполнения")
    @Email(message = "Некорректный формат email")
    @Schema(
            description = "Email пользователя",
            example = "user@example.com"
    )
    private String email;

    @NotBlank(message = "Пароль обязателен для заполнения")
    @Size(min = 4, max = 32, message = "Пароль должен содержать от 4 до 32 символов")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).*$",
            message = "Пароль должен содержать цифры, буквы в верхнем и нижнем регистре и спецсимволы"
    )
    @Schema(
            description = "Пароль пользователя",
            example = "SecurePass123!",
            minLength = 4,
            maxLength = 32
    )
    private String password;
}
