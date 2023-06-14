package com.cybrixsystems.apipm.Repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cybrixsystems.apipm.Model.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{
    public Optional<Product> findByName(String name);
    public List<Product> findAllByCategoriesName(String name);
    public List<Product> findAllByNameContainingOrBrandContainingOrCategoriesNameContaining(String name,String brand,String nameCat);
    
    @Query("SELECT p FROM Product p "
            +"JOIN p.categories c "
            +"WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) "+"OR "
            +"LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%')) "+"OR "
            +"LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    public List<Product> findAllBySearch(@Param("search") String search);
}
