package org.npeonelove.cartservice.repository;

import org.npeonelove.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query(value = "SELECT * FROM cart WHERE profile_id = :profileId AND product_id = :productId", nativeQuery = true)
    Optional<Cart> getCartItemsByProfileAndProduct(
            @Param("profileId") Integer profileId,
            @Param("productId") Integer productId
    );

    List<Cart> getCartsByProfileId(Integer profileId);

    List<Cart> findCartByProfileId(Integer profileId);

}
