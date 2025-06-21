package org.npeonelove.cartservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.npeonelove.cartservice.dto.AddProductDTO;
import org.npeonelove.cartservice.dto.CartProductDTO;
import org.npeonelove.cartservice.dto.DeleteProductDTO;
import org.npeonelove.cartservice.dto.EditProductDTO;
import org.npeonelove.cartservice.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Cart Management", description = "API для управления корзиной покупок")
public class CartController {

    private final CartService cartService;

    @Operation(
            summary = "Добавить продукт в корзину",
            description = "Добавляет указанный продукт в корзину пользователя"
    )
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

    @Operation(
            summary = "Изменить количество продукта в корзине",
            description = "Обновляет количество указанного продукта в корзине пользователя"
    )
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

    @Operation(
            summary = "Удалить продукт из корзины",
            description = "Удаляет указанный продукт из корзины пользователя"
    )
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

    @Operation(
            summary = "Получить все товары в корзине",
            description = "Возвращает список всех товаров, находящихся в корзине пользователя"
    )
    @GetMapping("/get-cart-items")
    public ResponseEntity<List<CartProductDTO>> getAllCartItems() {
        List<CartProductDTO> cartItems = cartService.getAllCarts();
        return ResponseEntity.ok(cartItems);
    }

    @Operation(
            summary = "Оплатить корзину",
            description = "Выполняет процесс оплаты всех товаров в корзине пользователя"
    )
    @PostMapping("/pay-for-cart")
    public void payForCart() {
        cartService.payCart();
    }
}