package org.shoppingcart.Convertor;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.shoppingcart.Model.DTO.CartItemDTO;
import org.shoppingcart.Model.DTO.ProductDTO;
import org.shoppingcart.Model.Entity.CartItemEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemConvertor {
    private final ModelMapper modelMapper;
    private final ProductConvertor productConvertor;

    public CartItemDTO entityToDTO(CartItemEntity cartItemEntity) {
        CartItemDTO cartItemDTO = modelMapper.map(cartItemEntity, CartItemDTO.class);
        ProductDTO productDTO = productConvertor.entityToDTO(cartItemEntity.getProduct());
        cartItemDTO.setProduct(productDTO);
        return cartItemDTO;
    }
}
