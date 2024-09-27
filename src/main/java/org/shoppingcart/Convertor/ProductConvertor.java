package org.shoppingcart.Convertor;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.shoppingcart.Model.DTO.ProductDTO;
import org.shoppingcart.Model.Entity.CategoryEntity;
import org.shoppingcart.Model.Entity.ProductEntity;
import org.shoppingcart.Model.Request.AddProductRequest;
import org.shoppingcart.Model.Request.UpdateProductRequest;
import org.shoppingcart.Repository.Category.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductConvertor {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final ImageConvertor imageConvertor;

    public ProductEntity addProductRequestToEntity(AddProductRequest request) {
        ProductEntity product = modelMapper.map(request, ProductEntity.class);
        // check category name
        // if yes, add category to entity
        // else save new category
        CategoryEntity category = Optional.ofNullable(categoryRepository.findByName(request.getCategoryName()))
                .orElseGet(() -> categoryRepository.save(new CategoryEntity(request.getCategoryName())));
        product.setCategory(category);
        return product;
    }

    public ProductEntity updateProductRequestToEntity(UpdateProductRequest request) {
        ProductEntity product = modelMapper.map(request, ProductEntity.class);
        product.setCategory(categoryRepository.findByName(request.getCategoryName()));
        return product;
    }

    public ProductDTO entityToDTO(ProductEntity productEntity) {
        ProductDTO product = modelMapper.map(productEntity, ProductDTO.class);
        product.setCategoryName(productEntity.getCategory().getName());
        if(product.getImages() != null) {
            product.setImages(productEntity.getImages().stream()
                    .map(imageConvertor::entityToDTO)
                    .toList());
        }
        return product;
    }
}