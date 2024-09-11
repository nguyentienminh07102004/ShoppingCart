package org.shoppingcart.Controller.Category;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.Exceptions.ResourceAlreadyExists;
import org.shoppingcart.Exceptions.ResourceNotFoundException;
import org.shoppingcart.Model.Entity.CategoryEntity;
import org.shoppingcart.Model.Response.APIResponse;
import org.shoppingcart.Service.Category.ICategoryService;
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
@RequestMapping(path = "${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping(path = "/all")
    public ResponseEntity<APIResponse> getAllCategories() {
        try {
            List<CategoryEntity> categories = categoryService.getAllCategories();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse("Found!", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // lỗi 500
                    .body(new APIResponse("Error!", e.getMessage()));
        }
    }

    @PostMapping(value = "/")
    public ResponseEntity<APIResponse> createCategory(@RequestBody CategoryEntity category) {
        try {
            CategoryEntity newCategory = categoryService.createCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new APIResponse("CREATED", newCategory));
        } catch (ResourceAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse("Add fail!", e.getMessage()));
        }
    }

    @GetMapping(value = "/id/{categoryId}")
    public ResponseEntity<APIResponse> getCategoryById(@PathVariable(value = "categoryId") String categoryId) {
        try {
            CategoryEntity category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse("FOUND!", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse("NOT FOUND!", e.getMessage()));
        }
    }

    @GetMapping(value = "/name/{categoryName}")
    public ResponseEntity<APIResponse> getCategoryByName(@PathVariable(value = "categoryName") String name) {
        try {
            List<CategoryEntity> categories = categoryService.getCategoryByName(name);
            return ResponseEntity.status(200).body(new APIResponse(null, categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Error!", e.getMessage()));
        }
    }

    @DeleteMapping(value = "/id/{categoryId}")
    public ResponseEntity<APIResponse> deleteCategoryById(@PathVariable(value = "categoryId") String id) {
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("SUCCESS!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse("NOT FOUND!", e.getMessage()));
        }
    }

    @PutMapping(value = "/")
    public ResponseEntity<APIResponse> updateCategory(@RequestBody CategoryEntity category) {
        try {
            CategoryEntity newCategory = categoryService.updateCategory(category);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse("SUCCESS!", newCategory));
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse("NOT FOUND!", exception.getMessage()));
        }
    }
}
