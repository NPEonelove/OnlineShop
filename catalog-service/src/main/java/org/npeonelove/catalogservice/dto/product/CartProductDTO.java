package org.npeonelove.catalogservice.dto.product;

import jakarta.persistence.*;
import lombok.*;
import org.npeonelove.catalogservice.model.Category;
import org.npeonelove.catalogservice.model.Photo;

import java.util.List;

@Getter
@Setter
public class CartProductDTO {

    private Long id;

    private String name;

    private String photo;

    private Double price;

    private Integer quantity;

}
