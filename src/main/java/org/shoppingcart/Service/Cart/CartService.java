package org.shoppingcart.Service.Cart;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.Exceptions.ResourceNotFoundException;
import org.shoppingcart.Model.Entity.CartEntity;
import org.shoppingcart.Model.Entity.CartItemEntity;
import org.shoppingcart.Repository.Cart.CartItemRepository;
import org.shoppingcart.Repository.Cart.CartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service(value = "CartService")
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartEntity createCart() {
        CartEntity cart = new CartEntity();
        return cartRepository.save(cart);
    }

    @Override
    public CartEntity getCartById(String id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
    }

    @Override
    public BigDecimal getTotalPriceCartById(String id) {
        CartEntity cart = getCartById(id);
        return cart.updatePrice();
    }

    @Override
    public void clearCartById(String id) {
        cartRepository.findById(id)
                .ifPresentOrElse((item) -> {
                    // xoá các items trong cart, không hiểu vì sao luôn,
                    // tưởng có orphanRemoval = true rồi là nó sẽ tự động xoá bên many
                    item.getCartItems().clear(); // không hiểu vì sao clear luôn
                    cartItemRepository.deleteAllByCart_Id(id);
                    cartRepository.delete(item);
                },
                        () -> {
                            throw new ResourceNotFoundException("Cart not found!");
                });
    }
}
