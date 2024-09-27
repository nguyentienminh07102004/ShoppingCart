package org.shoppingcart.Service.Cart;

import org.shoppingcart.Model.DTO.CartDTO;
import org.shoppingcart.Model.Entity.CartEntity;

import java.math.BigDecimal;

public interface ICartService {
    CartEntity createCart();
    CartDTO getCartById(String id);
    CartEntity getCartEntityById(String id);
    BigDecimal getTotalPriceCartById(String id);
    void clearCartById(String id);
}
