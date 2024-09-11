package org.shoppingcart.Repository.Image;

import org.shoppingcart.Model.Entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, String> {
}
