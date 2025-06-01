package org.npeonelove.catalogservice.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCardProductDTO {
    private Long id;
    private String name;
    private String photo;
    private Double price;
    private Double rating;
    private Integer reviewsCount;
}
