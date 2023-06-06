package com.cybrixsystems.apipm.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.cybrixsystems.apipm.Data;
import com.cybrixsystems.apipm.Model.Product;

public class IMPProductRepository implements ProductRepository{

    @Autowired
    CategoryRepository cr;

    @Override
    public List<Product> findAll() {
        System.out.println("IMPProductRepository.findAll");
        /* 
        List<Product> prList = Data.PRODUCTS;
        prList.forEach(pr -> pr.setCategories(cr.findAllByIdP(pr.getIdProduct())));
        */
    return Data.PRODUCTS;    
    }

    @Override
    public Optional<Product> findById(Long idP) {
        System.out.println("IMPProductRepository.findById: " + idP );
       return this.findAll()
                .stream()
                .filter(e -> e.getIdProduct().compareTo(idP) == 0)
                .findFirst();   
    }

    @Override
    public Optional<Product> findByName(String name) {
        System.out.println("IMPProductRepository.findByName: " + name );
       return this.findAll()
                .stream()
                .filter(e -> e.getName().compareTo(name) == 0)
                .findFirst();
    }

    @Override
    public Product save(Product product) {
        System.out.println("IMPProductRepository.save");
        product.setIdProduct(Long.valueOf(Data.PRODUCTS.size() + 1));
    return product;    
    }

    @Override
    public void deleteById(Long idP) {
        System.out.println("IMPProductRepository.deleteById");
        Optional<Product> cat = this.findById(idP);
        if(cat.isPresent()){
            this.findAll().remove(cat.get());
            System.err.println("IMPProductRepository.deleteById COMPLETE DELETE");
        }
    }
    
}
