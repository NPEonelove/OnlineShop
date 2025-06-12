package org.npeonelove.cartservice.client;

import org.npeonelove.cartservice.dto.CartProductDTO;
import org.npeonelove.cartservice.dto.PayForProductRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "catalog-service", url = "http://localhost:8081/catalog")
public interface CatalogFeignClient {

    @GetMapping("/product/get-products-for-cart")
    List<CartProductDTO> getProductsForCart(@RequestParam("ids") List<Long> ids);

    @PostMapping("/product/pay-for-product")
    void payForProduct(@RequestBody PayForProductRequestDTO payForProductRequestDTO);

}
