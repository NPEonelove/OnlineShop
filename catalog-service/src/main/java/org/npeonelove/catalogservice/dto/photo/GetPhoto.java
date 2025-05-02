package org.npeonelove.catalogservice.dto.photo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPhoto {
    private Long id;
    private Long productId;
    private String photolink;
}
