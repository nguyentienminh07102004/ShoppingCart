package org.shoppingcart.Repository.Category;

import org.shoppingcart.Model.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    List<CategoryEntity> findByNameContaining(String categoryName);
    CategoryEntity findByName(String name);
    Boolean existsByName(String id);
}
