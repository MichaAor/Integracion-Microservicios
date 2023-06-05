package com.cybrixsystems.apipm.Service;

import java.util.List;
import java.util.Optional;

import com.cybrixsystems.apipm.Model.Category;

public interface CategoryService {
    List<Category> findAllCategories();
    Optional<Category> findCategoryById(Long idC);
    Optional<Category> findCategoryByName(String name);
    Category save(Category category);
    void deleteCategoryById(Long idC);
}
