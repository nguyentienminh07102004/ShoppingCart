package org.shoppingcart.Model.Request;

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
public class AddProductRequest {
    private String id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private Integer inventory; // số lượng tồn kho
    private String categoryName;
}
