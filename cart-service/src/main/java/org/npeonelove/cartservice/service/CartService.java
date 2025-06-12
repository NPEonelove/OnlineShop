package org.npeonelove.cartservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.npeonelove.cartservice.client.CatalogFeignClient;
import org.npeonelove.cartservice.client.ProfileFeignClient;
import org.npeonelove.cartservice.dto.*;
import org.npeonelove.cartservice.model.Cart;
import org.npeonelove.cartservice.repository.CartRepository;
import org.npeonelove.cartservice.security.SecurityContextService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final ProfileFeignClient profileFeignClient;
    private final SecurityContextService securityContextService;
    private final CatalogFeignClient catalogFeignClient;
    private final ModelMapper modelMapper;

    @Transactional
    public void addProductToCart(AddProductDTO addProductDTO) {
        int profileId = Math.toIntExact(profileFeignClient.getProfileByEmail(securityContextService.getEmailFromSecurityContext()).getId());

        Cart cart = cartRepository.getCartItemsByProfileAndProduct(profileId, addProductDTO.getProductId()).orElse(null);
        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + 1);
            cartRepository.save(cart);
            return;
        }

        cart = new Cart();
        cart.setProfileId(profileId);
        cart.setProductId(addProductDTO.getProductId());
        cart.setQuantity(1);
        cartRepository.save(cart);
    }

    @Transactional
    public void editProductInCart(EditProductDTO editProductDTO) {

        int profileId = Math.toIntExact(profileFeignClient.getProfileByEmail(securityContextService.getEmailFromSecurityContext()).getId());

        if (editProductDTO == null) {
            throw new IllegalArgumentException("EditProductDTO cannot be null");
        }

        Cart cart = cartRepository.getCartItemsByProfileAndProduct(
                        profileId,
                        editProductDTO.getProductId()
                )
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Cart item not found for profileId: %d and productId: %d",
                                profileId,
                                editProductDTO.getProductId())
                ));

        if (editProductDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        cart.setQuantity(editProductDTO.getQuantity());
        cartRepository.save(cart);
    }

    @Transactional
    public void deleteProductFromCart(DeleteProductDTO deleteProductDTO) {
        int profileId = Math.toIntExact(profileFeignClient.getProfileByEmail(securityContextService.getEmailFromSecurityContext()).getId());
        if (deleteProductDTO == null) {
            throw new IllegalArgumentException("DeleteProductDTO cannot be null");
        }

        Cart cart = cartRepository.getCartItemsByProfileAndProduct(
                        profileId,
                        deleteProductDTO.getProductId()
                )
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Cart item not found for profileId: %d and productId: %d",
                                profileId,
                                deleteProductDTO.getProductId())
                ));

        cartRepository.delete(cart);
    }

    public List<CartProductDTO> getAllCarts() {

        int profileId = Math.toIntExact(profileFeignClient.getProfileByEmail(securityContextService.getEmailFromSecurityContext()).getId());
        List<Cart> carts = cartRepository.findCartByProfileId(profileId);
        if (carts == null || carts.isEmpty()) {
            return Collections.emptyList(); // или можно выбросить исключение, если нужно
        }

        List<Long> productIds = carts.stream()
                .map(cart -> Long.valueOf(cart.getProductId()))
                .toList();

        List<CartProductDTO> cartProductDTOList = new ArrayList<>();

        for (CartProductDTO cartProductDTO : catalogFeignClient.getProductsForCart(productIds)) {
            cartProductDTO.setQuantity(cartRepository.getCartItemsByProfileAndProduct(profileId, Math.toIntExact(cartProductDTO.getId())).get().getQuantity());
            cartProductDTOList.add(cartProductDTO);
        }
        return cartProductDTOList;
    }

    @Transactional
    public void payCart() {
        List<Cart> carts = cartRepository.getCartsByProfileId(Math.toIntExact(profileFeignClient.getProfileByEmail(securityContextService.getEmailFromSecurityContext()).getId()));
        for (Cart cart : carts) {
            catalogFeignClient.payForProduct(modelMapper.map(cart, PayForProductRequestDTO.class));
            cartRepository.delete(cart);
        }
    }

}
