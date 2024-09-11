package org.shoppingcart.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private String id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private Integer inventory;
    private String categoryName;
    private List<ImageDTO> images;
}
