package org.npeonelove.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.npeonelove.catalogservice.client.MediaFeignClient;
import org.npeonelove.catalogservice.dto.PayForProductRequestDTO;
import org.npeonelove.catalogservice.dto.product.*;
import org.npeonelove.catalogservice.exception.category.CategoryNotExistsException;
import org.npeonelove.catalogservice.exception.product.ProductAlreadyExistsException;
import org.npeonelove.catalogservice.exception.product.ProductNotExistsException;
import org.npeonelove.catalogservice.exception.product.ProductPhotoNotExistsException;
import org.npeonelove.catalogservice.model.Category;
import org.npeonelove.catalogservice.model.Product;
import org.npeonelove.catalogservice.repository.CategoryRepository;
import org.npeonelove.catalogservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    // получение всех карточек товаров
    // TODO: добавить пагинацию
    public List<GetCardProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(product ->
                modelMapper.map(product, GetCardProductDTO.class)).collect(Collectors.toList());
    }

    // получение 12 товаров для главной страницы
    public List<GetCardProductDTO> getProductsForMainPage() {
        return productRepository.findProductsForMainPage().stream().map(product ->
                modelMapper.map(product, GetCardProductDTO.class)).toList();
    }

    // добавление нового товара
    @Transactional
    public void addProduct(AddProductDTO addProductDTO) {
        if (productRepository.findByName(addProductDTO.getName()) != null) {
            throw new ProductAlreadyExistsException("Product already exists");
        }

        Product product = modelMapper.map(addProductDTO, Product.class);
        if (addProductDTO.getCategories() != null) {
            product.setCategories(getCategoryList(addProductDTO.getCategories()));
        }

        productRepository.save(product);
    }

    // получение всех данных о товаре
    public GetFullProductDTO getProduct(Long id) {
        Product product = productRepository.findProductById(id).orElseThrow(() -> new ProductNotExistsException("Product does not exist"));
        return modelMapper.map(product, GetFullProductDTO.class);
    }

    // редактирование товара по id
    @Transactional
    public void editProduct(Long id, EditProductDTO editProductDTO) {

        checkProductNameUnique(id, editProductDTO.getName());

        Product product = productRepository.findProductById(id).orElseThrow(() -> new ProductNotExistsException("Product does not exist"));
        product.setName(editProductDTO.getName());
        product.setDescription(editProductDTO.getDescription());
        product.setPrice(editProductDTO.getPrice());
        product.setCount(editProductDTO.getCount());
        if (editProductDTO.getCategories() != null) {
            product.setCategories(getCategoryList(editProductDTO.getCategories()));
        }

        productRepository.save(product);
    }

    // удаление товара по id
    @Transactional
    public void deleteProduct(Long id) {
        if (productRepository.findProductById(id).isEmpty()) {
            throw new ProductNotExistsException("Product does not exist");
        }
        productRepository.deleteById(id);
    }

    // получение всех категорий как объектов
    private List<Category> getCategoryList(long[] categoriesIds) {
        List<Category> categories = new ArrayList<>();
        if (categoriesIds.length == 0) {
            return Collections.emptyList();
        } else {
            for (long i : categoriesIds) {
                categories.add(categoryRepository.findCategoryById(i));
            }
            return categories;
        }
    }

    // проверка для редактирования объекта
    private void checkProductNameUnique(Long id, String name) {
        if (productRepository.findById(id).isEmpty()) {
            throw new CategoryNotExistsException("Product with this id does not exist");
        }

        Product product = productRepository.findByName(name);

        if (product != null) {
            if (!product.getId().equals(id)) {
                throw new CategoryNotExistsException("Product with this name already exist");
            }
        }
    }

    // получение списка товаров для корзины
    public List<CartProductDTO> getProductsForCart(List<Long> cartIds) {
        List<CartProductDTO> cartProductDTOList = new ArrayList<>();
        for (Long id : cartIds) {
            cartProductDTOList.add(modelMapper.map(productRepository.findProductById(id), CartProductDTO.class));
        }
        return cartProductDTOList;
    }


    // изменение количества товаров (для оплаты корзины)
    @Transactional
    public void payForProduct(PayForProductRequestDTO payForProductRequestDTO) {
        Product product = productRepository.findProductById(Long.valueOf(payForProductRequestDTO.getProductId())).orElse(null);
        if (product != null) {
            product.setCount(product.getCount() - payForProductRequestDTO.getQuantity());
            System.out.println(product.getDescription());
            productRepository.save(product);
        }
    }

    // потом будет удалено
    private final MediaFeignClient mediaFeignClient;

    @Transactional
    public String addProductPhoto(Long id, MultipartFile image) {
        String photoLink = mediaFeignClient.uploadMedia("catalog/productCard", new MultipartFile[]{image}).getFirst();
        Product product = productRepository.findProductById(id).get();
        if (product.getPhoto() != null) {
            deleteProductPhotoByProductId(product.getId());
        }
        product.setPhoto(photoLink);
        productRepository.save(product);
        return photoLink;
    }

    @Transactional
    public void deleteProductPhotoByProductId(Long id) {
        Product product = productRepository.findProductById(id).get();
        if (product.getPhoto() != null) {
            mediaFeignClient.deleteMedia(new String[]{product.getPhoto()});
        } else {
            throw new ProductPhotoNotExistsException("Product photo does not exist");
        }
        product.setPhoto(null);
        productRepository.save(product);
    }
}
