package org.shoppingcart.Service.Product;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.Convertor.ProductConvertor;
import org.shoppingcart.Exceptions.ResourceNotFoundException;
import org.shoppingcart.Model.DTO.ProductDTO;
import org.shoppingcart.Model.Entity.ProductEntity;
import org.shoppingcart.Model.Request.AddProductRequest;
import org.shoppingcart.Model.Request.UpdateProductRequest;
import org.shoppingcart.Repository.Product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ProductConvertor productConvertor;

    @Override
    public ProductDTO createProduct(AddProductRequest product) {
        ProductEntity productEntity = productConvertor.addProductRequestToEntity(product);
        return productConvertor.entityToDTO(productRepository.save(productEntity));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> list = productRepository.findAll();
        return list.stream()
                .map(productConvertor::entityToDTO)
                .toList();
    }

    @Override
    public ProductDTO getProductById(String id) {
        return productConvertor.entityToDTO(this.getProductEntityById(id));
    }

    @Override
    public ProductEntity getProductEntityById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found!"));
    }

    @Override
    public void deleteProductById(List<String> ids) {
        productRepository.findAllByIdIn(ids)
                .ifPresentOrElse(productRepository::deleteAll,
                () -> {
                    throw new ResourceNotFoundException("Product Not Found!");
                }
        );
    }

    @Override
    public ProductDTO updateProduct(UpdateProductRequest request) {
        return productRepository.findById(request.getId())
                .map(item -> productConvertor.updateProductRequestToEntity(request))
                .map(productRepository::save)
                .map(productConvertor::entityToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found!"));
    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(String categoryId) {
        return productRepository.findByCategory_Id(categoryId).stream()
                .map(productConvertor::entityToDTO)
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand).stream()
                .map(productConvertor::entityToDTO)
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsByName(String name) {
        return productRepository.findByNameContaining(name).stream()
                .map(productConvertor::entityToDTO)
                .toList();
    }

    @Override
    public Long countAllProducts() {
        return productRepository.count();
    }
}
