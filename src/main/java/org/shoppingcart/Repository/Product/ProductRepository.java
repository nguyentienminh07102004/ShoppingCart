package org.shoppingcart.Repository.Product;

import org.shoppingcart.Model.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "productRepository")
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    Optional<List<ProductEntity>> findAllByIdIn(List<String> ids);
    void deleteAllByIdIn(List<String> ids);
    List<ProductEntity> findByCategory_Id(String categoryId);
    List<ProductEntity> findByBrand(String brand);
    List<ProductEntity> findByNameContaining(String name);
}
