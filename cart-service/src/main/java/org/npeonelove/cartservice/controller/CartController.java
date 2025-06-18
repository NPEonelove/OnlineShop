package org.npeonelove.cartservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.npeonelove.cartservice.dto.*;
import org.npeonelove.cartservice.model.Cart;
import org.npeonelove.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add-product-in-the-cart")
    public ResponseEntity<String> addProductsInTheCart(@RequestBody @Valid AddProductDTO productDTO,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.append(error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }

        cartService.addProductToCart(productDTO);

        return ResponseEntity.ok("Product added in the cart");
    }

    @PostMapping("/edit-count-product-in-the-cart")
    public ResponseEntity<String> editCountProductInTheCart(@RequestBody @Valid EditProductDTO productDTO,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.append(error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }

        cartService.editProductInCart(productDTO);

        return ResponseEntity.ok("Product quantity updated in the cart");
    }

    @PostMapping("/delete-product-from-the-cart")
    public ResponseEntity<String> deleteProductFromTheCart(@RequestBody @Valid DeleteProductDTO productDTO,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.append(error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }

        cartService.deleteProductFromCart(productDTO);

        return ResponseEntity.ok("Product removed from the cart");
    }

    @GetMapping("/get-cart-items")
    public ResponseEntity<List<CartProductDTO>> getAllCartItems() {
        List<CartProductDTO> cartItems = cartService.getAllCarts();
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/pay-for-cart")
    public void payForCart() {
        cartService.payCart();
    }


}
