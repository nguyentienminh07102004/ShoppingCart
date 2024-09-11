package org.shoppingcart.Repository.Cart;

import org.shoppingcart.Model.Entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, String> {
    void deleteAllByCart_Id(String id);
}
