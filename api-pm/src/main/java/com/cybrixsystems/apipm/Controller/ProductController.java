package com.cybrixsystems.apipm.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{idP}")
    public ResponseEntity<Product> findByIdProduct(@PathVariable("idP") Long idP){
        Optional<Product> product = ps.findProductById(idP);
        if(product.isPresent()){
            return ResponseEntity.ok(product.orElseThrow());
        }else{
             return ResponseEntity.notFound().build();   
        }
    }

    // @GetMapping("/{nameP}")
    // public ResponseEntity<Product> findByNameProduct(@PathVariable("nameP") String nameP){
    //     Optional<Product> product = ps.findProductByName(nameP);
    //     if(product.isPresent()){
    //         return ResponseEntity.ok(product.orElseThrow());
    //     }else{
    //          return ResponseEntity.notFound().build();   
    //     }
    // }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        Product prodSaved = ps.saveORupdateProduct(product);
    return ResponseEntity.status(HttpStatus.CREATED).body(prodSaved);    
    }

    @PutMapping("/{idP}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product
                                                ,@PathVariable("idP") Long idP){
        product.setIdProduct(idP);
        Product prodUpd = ps.saveORupdateProduct(product);
        if(! product.equals(prodUpd)){
            return ResponseEntity.ok(prodUpd);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idP}")
    public ResponseEntity<?> deleteProduct(@PathVariable("idP")Long idP){
        return (ps.deleteProductById(idP)) ? 
                        ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
