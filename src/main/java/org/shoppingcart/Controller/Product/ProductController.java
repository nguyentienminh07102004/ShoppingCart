package org.shoppingcart.Controller.Product;

import  lombok.RequiredArgsConstructor;
import org.shoppingcart.Model.DTO.ProductDTO;
import org.shoppingcart.Model.Entity.ProductEntity;
import org.shoppingcart.Model.Request.AddProductRequest;
import org.shoppingcart.Model.Request.UpdateProductRequest;
import org.shoppingcart.Model.Response.APIResponse;
import org.shoppingcart.Service.Product.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping(value = "/all")
    public ResponseEntity<APIResponse> getAllProducts() {
        try {
            List<ProductDTO> list = productService.getAllProducts();
            return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("FOUND!", list));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Error!", exception.getMessage()));
        }
    }

    @GetMapping(value = "/id/{productId}")
    public ResponseEntity<APIResponse> getProductById(@PathVariable(value = "productId") String id) {
        try {
            ProductDTO product = productService.getProductById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse("FOUND!", product));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse("NOT FOUND", exception.getMessage()));
        }
    }

    @PostMapping(value = "/")
    public ResponseEntity<APIResponse> createProduct(@RequestBody AddProductRequest request) {
        try {
            ProductDTO product = productService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new APIResponse("CREATED", product));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("CREATE FAIL!", exception.getMessage()));
        }
    }

    @PutMapping(value = "/")
    public ResponseEntity<APIResponse> updateProduct(@RequestBody UpdateProductRequest request) {
        try {
            ProductDTO product = productService.updateProduct(request);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse("UPDATE SUCCESS", product));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse("UPDATE FAIL", exception.getMessage()));
        }
    }

    @DeleteMapping(value = "/ids/{ids}")
    public ResponseEntity<APIResponse> deleteProductsById(@PathVariable(value = "ids") List<String> ids) {
        try {
            productService.deleteProductById(ids);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse("DELETE SUCCESS!", null));
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(new APIResponse("DELETE FAIL!", e.getMessage()));
        }
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<APIResponse> getProductsByName(@PathVariable(value = "name") String name) {
        try {
            List<ProductDTO> products = productService.getProductsByName(name);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse("FOUND!", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Error!", e.getMessage()));
        }
    }

    @GetMapping(value = "/count")
    public ResponseEntity<APIResponse> countProducts() {
        try {
            var count = productService.countAllProducts();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse("SUCCESS!", count));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Error!", exception.getMessage()));
        }
    }
}
