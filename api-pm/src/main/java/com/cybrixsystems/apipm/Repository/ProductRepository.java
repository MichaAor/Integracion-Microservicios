package com.cybrixsystems.apipm.Repository;

import java.util.List;
import java.util.Optional;

import com.cybrixsystems.apipm.Model.Product;

public interface ProductRepository{
    public List<Product> findAll();
    public Optional<Product> findById(Long idP);
    public Product save(Product Product);
    public void deleteById(Long idP);
}
