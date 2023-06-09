package com.cybrixsystems.apipm.Repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.jdbc.Sql;

import com.cybrixsystems.apipm.Model.Product;

@DataJpaTest
//@Sql("/impProduct.sql")
public class INTProdRepoTest {
    @Autowired
    private ProductRepository pr;

    @Test
    void findAll_test(){
        List<Product> products = pr.findAll();
        assertFalse(products.isEmpty());
    }

    @Test
    void findById_test(){
        Optional<Product> product = pr.findById(2L);

        assertTrue(product.isPresent());
        assertFalse(product.isEmpty());
        assertEquals(2L, product.orElseThrow().getIdProduct());
        assertEquals("Spiderman - Back in black 1", product.orElseThrow().getName());
        assertEquals("Marvel", product.orElseThrow().getBrand());
        assertEquals(10, product.orElseThrow().getStock());
        assertEquals(5700f, product.orElseThrow().getUnitPrice());
        assertEquals("2007-04-20", product.orElseThrow().getReleaseDate().toString());
    }

    @Test
    void findByName_test(){
        Optional<Product> product = pr.findByName("Spiderman - Back in black 1");

        assertTrue(product.isPresent());
        assertFalse(product.isEmpty());
        assertEquals(2L, product.orElseThrow().getIdProduct());
        assertEquals("Spiderman - Back in black 1", product.orElseThrow().getName());
        assertEquals("Marvel", product.orElseThrow().getBrand());
        assertEquals(10, product.orElseThrow().getStock());
        assertEquals(5700f, product.orElseThrow().getUnitPrice());
        assertEquals("2007-04-20", product.orElseThrow().getReleaseDate().toString());
    }

    @Test
    void deleteById_test(){
        int sizeBefore = pr.findAll().size();
        pr.deleteById(2L);
        List<Product> products = pr.findAll();

        assertFalse(products.isEmpty());
        assertEquals(sizeBefore -1,products.size());
        assertFalse(products.stream().anyMatch(p -> p.getIdProduct().equals(2L)));
    }
    
}
