package org.shoppingcart.Service.Cart;

import org.shoppingcart.Model.Entity.CartEntity;

import java.math.BigDecimal;

public interface ICartService {
    CartEntity createCart();
    CartEntity getCartById(String id);
    BigDecimal getTotalPriceCartById(String id);
    void clearCartById(String id);
}
