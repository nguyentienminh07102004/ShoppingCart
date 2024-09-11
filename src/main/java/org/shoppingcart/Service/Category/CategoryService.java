package org.shoppingcart.Service.Category;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.Exceptions.ResourceAlreadyExists;
import org.shoppingcart.Exceptions.ResourceNotFoundException;
import org.shoppingcart.Model.Entity.CategoryEntity;
import org.shoppingcart.Repository.Category.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "CategoryService")
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryEntity getCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found Exception!"));
    }

    @Override
    public List<CategoryEntity> getCategoryByName(String name) {
        return categoryRepository.findByNameContaining(name);
    }

    @Override
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getId()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new ResourceAlreadyExists("Category " + category.getName() + " is exists"));
    }

    @Override
    public CategoryEntity updateCategory(CategoryEntity category) {
        return categoryRepository.findById(category.getId())
                .map(categoryRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found!"));
    }

    @Override
    public void deleteCategoryById(String id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Category Not Found!");
                        });
    }
}
