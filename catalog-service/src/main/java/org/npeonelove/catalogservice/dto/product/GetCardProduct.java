package org.npeonelove.catalogservice.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCardProduct {
    private Long id;
    private String name;
    private String photo;
    private Double price;
    private Double rating;
}
