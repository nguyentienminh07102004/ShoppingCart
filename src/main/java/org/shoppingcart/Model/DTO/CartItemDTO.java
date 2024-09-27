package org.shoppingcart.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {
    private String id;
    private ProductDTO product;
    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal totalPrice;
}
