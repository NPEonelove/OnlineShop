package org.npeonelove.catalogservice.repository;

import org.npeonelove.catalogservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);

    Optional<Product> findProductById(Long id);

    @Query(value = "select * from products limit 12", nativeQuery = true)
    List<Product> findProductsForMainPage();

}
