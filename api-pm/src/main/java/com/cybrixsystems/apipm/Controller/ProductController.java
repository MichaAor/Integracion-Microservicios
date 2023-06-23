package com.cybrixsystems.apipm.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybrixsystems.apipm.Model.Product;
import com.cybrixsystems.apipm.Service.ProductService;

@RestController
@RequestMapping("/apiPM/Products")
public class ProductController {
    @Autowired
    private ProductService ps;

    @GetMapping
    public ResponseEntity<List<Product>> findAllProducts(){
        return ResponseEntity.ok(ps.findAllProducts());
    }
    
}
