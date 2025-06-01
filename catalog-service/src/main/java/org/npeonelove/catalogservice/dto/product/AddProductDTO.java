package org.npeonelove.catalogservice.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddProductDTO {
    @NotNull(message = "Введите название")
    @Size(min = 1, max = 255, message = "Длина названия от 1 до 255 символов")
    private String name;

    @Size(max = 2000, message = "Длина описания до 2000 символов")
    private String description;

    @NotNull(message = "Введите цену")
    @Positive(message = "Цена должна быть числом больше 0")
    private Double price;

    @NotNull(message = "Введите количество")
    @Positive(message = "Количество должно быть числом больше 0")
    private Integer count;

    private long[] categories;
}
