package com.cybrixsystems.apipm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybrixsystems.apipm.Model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
