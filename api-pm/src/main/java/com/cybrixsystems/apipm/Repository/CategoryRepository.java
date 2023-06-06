package com.cybrixsystems.apipm.Repository;


import java.util.List;
import java.util.Optional;

import com.cybrixsystems.apipm.Model.Category;

public interface CategoryRepository {
    public List<Category> findAll();
    public List<Category> findAllByIdP(Long idP);
    public Optional<Category> findById(Long idC);
    public Optional<Category> findByName(String name);
    public Category save(Category category);
    public void deleteById(Long idC);
}
