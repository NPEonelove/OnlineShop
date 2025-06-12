package org.npeonelove.cartservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditProductDTO {

    //    @NotBlank(message = "Укажите Product ID")
    private Integer productId;

    //    @NotBlank(message = "Укажите количество добавляемых товаров")
//    @Positive(message = "Число должно быть больше 0")
    private Integer quantity;

}
