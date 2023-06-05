package com.cybrixsystems.apipm.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.cybrixsystems.apipm.Model.Category;
import com.cybrixsystems.apipm.Repository.CategoryRepository;

public class IMPCategoryService implements CategoryService{
    @Autowired
    private CategoryRepository cr;

    @Override
    public List<Category> findAllCategories() {
        return cr.findAll();
    }

    @Override
    public Optional<Category> findCategoryById(Long idC) {
        return cr.findById(idC);
    }

    @Override
    public Optional<Category> findCategoryByName(String name) {
        return cr.findByName(name);
    }

    @Override
    public Category save(Category category) {
        return cr.save(category);
    }

    @Override
    public void deleteCategoryById(Long idC) {
        cr.deleteById(idC);
    }
}
