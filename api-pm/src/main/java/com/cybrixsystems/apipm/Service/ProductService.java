package com.cybrixsystems.apipm.Service;

import java.util.List;
import java.util.Optional;

import com.cybrixsystems.apipm.Model.Product;

public interface ProductService {
    List<Product> findAllProducts();
    Optional<Product> findProductById(Long idP);
    Optional<Product> findProductByName(String name);
    Product save(Product product);
    void deleteProductById(Long idC);
}
