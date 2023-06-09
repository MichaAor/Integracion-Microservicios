package com.cybrixsystems.apipm.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybrixsystems.apipm.Model.Category;


public interface CategoryRepository extends JpaRepository<Category,Long>{
    public Optional<Category> findByName(String name);
}
