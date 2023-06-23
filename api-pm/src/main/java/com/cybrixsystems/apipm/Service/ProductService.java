package com.cybrixsystems.apipm.Service;

import java.util.List;
import java.util.Optional;

import com.cybrixsystems.apipm.Model.Product;

public interface ProductService {
    List<Product> findAllProducts();
    List<Product> findAllProductsByCategoryName(String catName);
    List<Product> findAllProductsBySearch(String search);
    Optional<Product> findProductById(Long idP);
    Optional<Product> findProductByName(String name);
    Product saveORupdateProduct(Product product);
    boolean deleteProductById(Long idC);
}
