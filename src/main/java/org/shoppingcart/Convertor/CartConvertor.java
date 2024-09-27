package org.shoppingcart.Convertor;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.shoppingcart.Model.DTO.CartDTO;
import org.shoppingcart.Model.DTO.CartItemDTO;
import org.shoppingcart.Model.Entity.CartEntity;
import org.shoppingcart.Model.Entity.CartItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartConvertor {
    private final ModelMapper modelMapper;
    private final CartItemConvertor cartItemConvertor;

    public CartDTO entityToDTO(CartEntity cartEntity) {
        CartDTO cartDTO = modelMapper.map(cartEntity, CartDTO.class);
        List<CartItemDTO> cartItemDTO = cartEntity.getCartItems().stream()
                .map(cartItemConvertor::entityToDTO)
                .toList();
        cartDTO.setCartItems(cartItemDTO);
        return cartDTO;
    }
}
