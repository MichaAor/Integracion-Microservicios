package com.cybrixsystems.apipm.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybrixsystems.apipm.Model.Product;
import com.cybrixsystems.apipm.Repository.ProductRepository;

@Service
public class IMPProductService implements ProductService{
    @Autowired
    private ProductRepository pr;

    @Override
    public List<Product> findAllProducts() {
        return pr.findAll();
    }

    @Override
    public Optional<Product> findProductById(Long idP) {
        return pr.findById(idP);
    }

    @Override
    public Optional<Product> findProductByName(String name) {
        return pr.findByName(name);
    }

    @Override
    public Product save(Product category) {
        return pr.save(category);
    }

    @Override
    public void deleteProductById(Long idP) {
        pr.deleteById(idP);
    }
}
