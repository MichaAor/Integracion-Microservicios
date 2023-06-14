package com.cybrixsystems.apipm.Repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cybrixsystems.apipm.Model.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{
    public Optional<Product> findByName(String name);
    public List<Product> findAllByCategoriesName(String name);
    public List<Product> findAllByNameContainingOrBrandContainingOrCategoriesNameContaining(String name,String brand,String nameCat);
}
