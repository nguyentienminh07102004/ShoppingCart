package org.shoppingcart.Repository.Cart;

import org.shoppingcart.Model.Entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, String> {
}
