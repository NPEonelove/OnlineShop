package org.npeonelove.catalogservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.npeonelove.catalogservice.dto.category.AddCategoryDTO;
import org.npeonelove.catalogservice.dto.category.EditCategoryDTO;
import org.npeonelove.catalogservice.dto.category.GetCategoryDTO;
import org.npeonelove.catalogservice.dto.product.GetCardProductDTO;
import org.npeonelove.catalogservice.exception.category.CategoryNotCreatedException;
import org.npeonelove.catalogservice.exception.category.CategoryNotEditedException;
import org.npeonelove.catalogservice.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Hidden
public class CategoryController {
    private final CategoryService categoryService;

    // получить все существующие категории
    @GetMapping("/get-all-exists-categories")
    public ResponseEntity<List<GetCategoryDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    // получить все продукты категории
    @GetMapping("/get-products-by-category")
    public ResponseEntity<List<GetCardProductDTO>> getProductsByCategory(@RequestBody GetCategoryDTO getCategoryDTO) {
        return ResponseEntity.ok(categoryService.findProductsByCategoryName(getCategoryDTO.getName()));
    }

    // создать новую категорию
    @PostMapping("/create-category")
    public ResponseEntity<AddCategoryDTO> createCategory(@RequestBody @Valid AddCategoryDTO addCategoryDTO,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMsg.append(fieldError.getDefaultMessage());
            }
            throw new CategoryNotCreatedException(errorMsg.toString());
        }
        return ResponseEntity.ok(categoryService.addCategory(addCategoryDTO));
    }

    // отредактировать категорию по id
    @PatchMapping("/edit-category-by-id/{id}")
    public ResponseEntity<EditCategoryDTO> editCategory(@PathVariable("id") Long id,
                                                        @RequestBody @Valid EditCategoryDTO editCategoryDTO,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMsg.append(fieldError.getDefaultMessage());
            }
            throw new CategoryNotEditedException(errorMsg.toString());
        }
        return ResponseEntity.ok(categoryService.editCategory(id, editCategoryDTO));
    }

    // удалить категорию по id
    @DeleteMapping("/delete-category-by-id/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
