package org.shoppingcart.Service.Cart;

public interface ICartItemService {
    void addCartItemToCart(String cartId, String productId, Integer quantity);
    void removeItemFromCart(String cartId, String productId);
    void updateCartItemQuantity(String cartId, String productId, Integer quantity);
}
