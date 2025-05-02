package org.npeonelove.catalogservice.dto.product;

import lombok.Getter;
import lombok.Setter;
import org.npeonelove.catalogservice.dto.category.GetCategory;

import java.util.List;

@Getter
@Setter
public class GetFullProduct {
    private Long id;

    private String name;

    private String description;

    private Double price;

    private String photo;

    private Integer count;

    private Double rating;

    List<GetCategory> categories;
}
