package org.shoppingcart.Service.Category;

import org.shoppingcart.Model.Entity.CategoryEntity;

import java.util.List;

public interface ICategoryService {
    CategoryEntity getCategoryById(String id);
    List<CategoryEntity> getCategoryByName(String name);
    List<CategoryEntity> getAllCategories();
    CategoryEntity createCategory(CategoryEntity category);
    CategoryEntity updateCategory(CategoryEntity category);
    void deleteCategoryById(String id);
}
