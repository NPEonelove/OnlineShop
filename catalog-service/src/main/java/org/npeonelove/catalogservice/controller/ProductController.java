package org.npeonelove.catalogservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.npeonelove.catalogservice.dto.product.AddProductDTO;
import org.npeonelove.catalogservice.dto.product.EditProductDTO;
import org.npeonelove.catalogservice.dto.product.GetCardProductDTO;
import org.npeonelove.catalogservice.dto.product.GetFullProductDTO;
import org.npeonelove.catalogservice.exception.product.ProductNotCreatedException;
import org.npeonelove.catalogservice.exception.product.ProductNotEditedException;
import org.npeonelove.catalogservice.service.PhotoService;
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
public class ProductController {
    private final ProductService productService;

    // получение всех карточек товаров
    // TODO: добавить пагинацию
    @GetMapping("/get-all-products")
    public ResponseEntity<List<GetCardProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // получение 12 товаров для главной страницы
    @GetMapping("/get-products-for-main-page")
    public ResponseEntity<List<GetCardProductDTO>> getProductsForMainPage() {
        return ResponseEntity.ok(productService.getProductsForMainPage());
    }

    // добавление нового товара
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

    // получение товара по id
    @GetMapping("/get-product-by-id/{id}")
    public ResponseEntity<GetFullProductDTO> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    // редактирование товара по id
    @PatchMapping("/edit-product-by-id/{id}")
    public ResponseEntity<HttpStatus> updateProduct(@PathVariable("id") Long id,
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

    // удаление товара по id
    @DeleteMapping("/delete-product-by-id/{id}")
    public HttpStatus deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return HttpStatus.OK;
    }

    @PostMapping("/add-product-photo/{id}")
    public ResponseEntity<String> addProductPhoto(@PathVariable("id") Long id, MultipartFile image) {
        return ResponseEntity.ok(productService.addProductPhoto(id, image));
    }

    @DeleteMapping("/delete-product-photo/{id}")
    public HttpStatus deleteProductPhoto(@PathVariable("id") Long id) {
        productService.deleteProductPhotoByProductId(id);
        return HttpStatus.OK;
    }
}
