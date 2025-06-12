package org.npeonelove.cartservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductDTO {

//    @NotBlank(message = "Укажите Product ID")
    private Integer productId;

}
