package org.shoppingcart.Service.Cart;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.Convertor.CartConvertor;
import org.shoppingcart.Exceptions.ResourceNotFoundException;
import org.shoppingcart.Model.DTO.CartDTO;
import org.shoppingcart.Model.Entity.CartEntity;
import org.shoppingcart.Repository.Cart.CartItemRepository;
import org.shoppingcart.Repository.Cart.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service(value = "CartService")
@RequiredArgsConstructor
@Transactional
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartConvertor cartConvertor;

    @Override
    public CartEntity createCart() {
        CartEntity cart = new CartEntity();
        return cartRepository.save(cart);
    }

    @Override
    public CartDTO getCartById(String id) {
        return cartConvertor.entityToDTO(getCartEntityById(id));
    }

    @Override
    public CartEntity getCartEntityById(String id) {
        CartEntity cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public BigDecimal getTotalPriceCartById(String id) {
        CartEntity cart = getCartEntityById(id);
        return cart.getTotalAmount();
    }

    @Override
    public void clearCartById(String id) {
        CartEntity cart = getCartEntityById(id);
        cartItemRepository.deleteByCart_id(id);
        cart.getCartItems().clear(); // chưa hiểu vì sao phải làm như này
        cartRepository.delete(cart);
    }
}
