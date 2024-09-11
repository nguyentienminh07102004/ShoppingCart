package org.shoppingcart.Service.Product;

import org.shoppingcart.Model.DTO.ProductDTO;
import org.shoppingcart.Model.Entity.ProductEntity;
import org.shoppingcart.Model.Request.AddProductRequest;
import org.shoppingcart.Model.Request.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    ProductDTO createProduct(AddProductRequest product);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(String id);
    ProductEntity getProductEntityById(String id);
    void deleteProductById(List<String> ids);
    ProductDTO updateProduct(UpdateProductRequest request);
    List<ProductDTO> getProductsByCategoryId(String categoryId);
    List<ProductDTO> getProductsByBrand(String brand);
    List<ProductDTO> getProductsByName(String name);
    Long countAllProducts();
}
