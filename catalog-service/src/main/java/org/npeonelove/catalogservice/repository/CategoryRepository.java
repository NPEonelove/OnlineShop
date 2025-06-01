package org.npeonelove.catalogservice.repository;

import org.npeonelove.catalogservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    Category findCategoryById(Long id);
}
