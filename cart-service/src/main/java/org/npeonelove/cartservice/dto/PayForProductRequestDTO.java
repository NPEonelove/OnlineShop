package org.npeonelove.cartservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayForProductRequestDTO {

    private Integer productId;
    private Integer quantity;

}
