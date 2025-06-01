package org.npeonelove.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.npeonelove.catalogservice.dto.category.AddCategoryDTO;
import org.npeonelove.catalogservice.dto.category.EditCategoryDTO;
import org.npeonelove.catalogservice.dto.category.GetCategoryDTO;
import org.npeonelove.catalogservice.dto.product.GetCardProductDTO;
import org.npeonelove.catalogservice.exception.category.CategoryAlreadyExistsException;
import org.npeonelove.catalogservice.exception.category.CategoryNotExistsException;
import org.npeonelove.catalogservice.exception.product.ProductNotExistsException;
import org.npeonelove.catalogservice.model.Category;
import org.npeonelove.catalogservice.model.Product;
import org.npeonelove.catalogservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<GetCategoryDTO> findAllCategories() {
        return categoryRepository.findAll().stream().map(category ->
                modelMapper.map(category, GetCategoryDTO.class))
                .collect(Collectors.toList());
    }

    public List<GetCardProductDTO> findProductsByCategoryName(String name) {
         Category category = categoryRepository.findByName(name);
         if (category == null) {
             throw new CategoryNotExistsException("Category does not exist");
         }
         List<Product> products = category.getProducts();
         List<GetCardProductDTO> productList = new ArrayList<>();
         for (Product product : products) {
             GetCardProductDTO getCardProductDTO = modelMapper.map(product, GetCardProductDTO.class);
             productList.add(getCardProductDTO);
         }
         return productList;
    }

    @Transactional
    public AddCategoryDTO addCategory(AddCategoryDTO addCategoryDTO) {
        if (categoryRepository.findByName(addCategoryDTO.getName()) == null) {
            categoryRepository.save(modelMapper.map(addCategoryDTO, Category.class));
        } else {
            throw new CategoryAlreadyExistsException("Category with name " + addCategoryDTO.getName() + " already exists");
        }
        return addCategoryDTO;
    }

    @Transactional
    public EditCategoryDTO editCategory(Long id, EditCategoryDTO editCategoryDTO) {

        checkCategoryNameUnique(id, editCategoryDTO.getName());

        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotExistsException("Category does not exist"));
        category.setName(editCategoryDTO.getName());
        categoryRepository.save(category);
        return editCategoryDTO;
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new CategoryNotExistsException("Category does not exist"));
        categoryRepository.deleteById(id);
    }

    private void checkCategoryNameUnique(Long id, String name) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new ProductNotExistsException("Category with this id does not exist");
        }

        Category category = categoryRepository.findByName(name);

        if (category != null) {
            if (!category.getId().equals(id)) {
                throw new ProductNotExistsException("Category with this name already exist");
            }
        }
    }
}
