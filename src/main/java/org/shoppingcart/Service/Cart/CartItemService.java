package org.shoppingcart.Service.Cart;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.Exceptions.ResourceNotFoundException;
import org.shoppingcart.Model.DTO.ProductDTO;
import org.shoppingcart.Model.Entity.CartEntity;
import org.shoppingcart.Model.Entity.CartItemEntity;
import org.shoppingcart.Model.Entity.ProductEntity;
import org.shoppingcart.Repository.Cart.CartItemRepository;
import org.shoppingcart.Repository.Cart.CartRepository;
import org.shoppingcart.Service.Product.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service(value = "CartItemService")
@RequiredArgsConstructor
@Transactional
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;

    @Override
    public void addCartItemToCart(String cartId, String productId, Integer quantity) {
        // 1.Lấy cart
        // 2. Lấy sản phẩm
        // 3. Kiểm tra sự tồn tại của sản phẩm trong giỏ hàng
        // 4. Nếu có rồi thì cập nhật lại số lượng
        // 5. Nếu chưa có thì khởi tạo
        CartEntity cart = cartService.getCartEntityById(cartId);
        ProductEntity product = productService.getProductEntityById(productId);
        CartItemEntity cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findAny()
                .orElse(new CartItemEntity());
        // Nếu không tìm thấy thì cartItem.getId() == null thì sẽ set các thuộc tính vào
        if(cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            // Nếu tồn tại thì cập nhật lại số lượng
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(String cartId, String productId) {
        CartEntity cart = cartService.getCartEntityById(cartId);
        ProductEntity product = productService.getProductEntityById(productId);
        CartItemEntity cartItem = cart.getCartItems().stream()
                .filter(item -> productId.equals(item.getProduct().getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not had in this cart"));
        cart.removeItem(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void updateCartItemQuantity(String cartId, String productId, Integer quantity) {
        CartEntity cart = cartService.getCartEntityById(cartId);
        ProductDTO product = productService.getProductById(productId);
        cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getCartItems().stream()
                .map(CartItemEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.saveAndFlush(cart);
    }
}
