package org.npeonelove.cartservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProductDTO {

    private long id;

    private String name;

    private String photo;

    private Double price;

    private Integer quantity;

}
