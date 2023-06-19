package com.cybrixsystems.apipm.Service;

import java.time.LocalDateTime;
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
    public List<Product> findAllProductsByCategoryName(String catName) {
        return pr.findAllByCategoriesName(catName);
    }

    @Override
    public List<Product> findAllProductsBySearch(String search) {
       return pr.findAllBySearch(search);
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
    public Product saveORupdateProduct(Product product) {
        /*
                 ?   Utilizo el método map de Optional para transformar el objeto "prodToUpd" 
                 ?   copiando los valores relevantes de "product" a través de la función lambda. 
                 ?   Si "prodExist" no está presente, es decir, el objeto no existe en la base de datos, 
                 ?   simplemente retornamos "category" sin realizar ningún cambio.
        */
        if(product.getIdProduct() != null){
            Optional<Product> prodExist = pr.findById(product.getIdProduct());
            Product prodToUpd = prodExist.map(c -> {
                                                c.setName(product.getName());
                                                c.setBrand(product.getBrand());
                                                c.setStock(product.getStock());
                                                c.setUnitPrice(product.getUnitPrice());
                                                c.setReleaseDate(product.getReleaseDate());
                                                c.setLastModDate(LocalDateTime.now());
                                            return c;    
                                            }).orElseGet(()-> product); 
            return pr.save(prodToUpd);                                
        }
        return pr.save(product);
    }

    @Override
    public void deleteProductById(Long idP) {
        pr.deleteById(idP);
    }
}
