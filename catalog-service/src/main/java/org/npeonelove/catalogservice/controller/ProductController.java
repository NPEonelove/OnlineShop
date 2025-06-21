package org.npeonelove.catalogservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.npeonelove.catalogservice.dto.PayForProductRequestDTO;
import org.npeonelove.catalogservice.dto.product.*;
import org.npeonelove.catalogservice.exception.product.ProductNotCreatedException;
import org.npeonelove.catalogservice.exception.product.ProductNotEditedException;
import org.npeonelove.catalogservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "API для управления товарами")
public class ProductController {
    private final ProductService productService;

    @Operation(
            summary = "Получить все товары",
            description = "Возвращает список всех товаров в каталоге"
    )
    @GetMapping("/get-all-products")
    public ResponseEntity<List<GetCardProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(
            summary = "Получить товары для главной страницы",
            description = "Возвращает 12 товаров для отображения на главной странице"
    )
    @GetMapping("/get-products-for-main-page")
    public ResponseEntity<List<GetCardProductDTO>> getProductsForMainPage() {
        return ResponseEntity.ok(productService.getProductsForMainPage());
    }

    @Operation(
            summary = "Добавить новый товар",
            description = "Создает новую карточку товара в каталоге"
    )
    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestBody @Valid AddProductDTO addProductDTO,
                                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMsg.append(error.getDefaultMessage()).append(". ");
            }
            throw new ProductNotCreatedException(errorMsg.toString());
        }

        productService.addProduct(addProductDTO);

        return ResponseEntity.ok("Product added successfully.");
    }

    @Operation(
            summary = "Получить товар по ID",
            description = "Возвращает полную информацию о товаре по его идентификатору"
    )
    @GetMapping("/get-product-by-id/{id}")
    public ResponseEntity<GetFullProductDTO> getProduct(
            @Parameter(description = "ID товара", required = true)
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @Operation(
            summary = "Редактировать товар",
            description = "Обновляет информацию о товаре по его идентификатору"
    )
    @PatchMapping("/edit-product-by-id/{id}")
    public ResponseEntity<HttpStatus> updateProduct(
            @Parameter(description = "ID товара", required = true)
            @PathVariable("id") Long id,
            @RequestBody @Valid EditProductDTO editProductDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMsg.append(error.getDefaultMessage()).append(". ");
            }
            throw new ProductNotEditedException(errorMsg.toString());
        }

        productService.editProduct(id, editProductDTO);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Удалить товар",
            description = "Удаляет товар по его идентификатору"
    )
    @DeleteMapping("/delete-product-by-id/{id}")
    public HttpStatus deleteProduct(
            @Parameter(description = "ID товара", required = true)
            @PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return HttpStatus.OK;
    }

    @Operation(
            summary = "Добавить фото товара",
            description = "Загружает изображение для указанного товара"
    )
    @PostMapping("/add-product-photo/{id}")
    public ResponseEntity<String> addProductPhoto(
            @Parameter(description = "ID товара", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Файл изображения", required = true)
            MultipartFile image) {
        return ResponseEntity.ok(productService.addProductPhoto(id, image));
    }

    @Operation(
            summary = "Удалить фото товара",
            description = "Удаляет изображение для указанного товара"
    )
    @DeleteMapping("/delete-product-photo/{id}")
    public HttpStatus deleteProductPhoto(
            @Parameter(description = "ID товара", required = true)
            @PathVariable("id") Long id) {
        productService.deleteProductPhotoByProductId(id);
        return HttpStatus.OK;
    }

    @Operation(
            summary = "Получить товары для корзины",
            description = "Возвращает список товаров по их ID"
    )
    @GetMapping("/get-products-for-cart")
    @Hidden
    public ResponseEntity<List<CartProductDTO>> getProductsForCart(
            @Parameter(description = "Список ID товаров", required = true)
            @RequestParam("ids") List<Long> ids) {
        return ResponseEntity.ok(productService.getProductsForCart(ids));
    }

    @Operation(
            summary = "Оплатить товар",
            description = "Выполняет оплату указанного товара"
    )
    @PostMapping("/pay-for-product")
    @Hidden
    public void payForProduct(@RequestBody PayForProductRequestDTO payForProductRequestDTO) {
        productService.payForProduct(payForProductRequestDTO);
    }
}