package com.cybrixsystems.apipm.Repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// import java.util.stream.Collectors;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.annotation.DirtiesContext;

import com.cybrixsystems.apipm.Model.Category;
import com.cybrixsystems.apipm.Model.Product;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// @Sql("/impProduct.sql")
@Sql("/import.sql")
public class INTProdRepoTest {
    @Autowired
    private ProductRepository pr;

    // @Autowired
    // private CategoryRepository cr;

    @Nested
    public class BasicTests {
        @Test
        void findAll_test() {
            List<Product> products = pr.findAll();
            assertFalse(products.isEmpty());
        }

        @Test
        void findById_test() {
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
        void findByName_test() {
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
        void deleteById_test() {
            int sizeBefore = pr.findAll().size();
            pr.deleteById(2L);
            List<Product> products = pr.findAll();

            assertFalse(products.isEmpty());
            assertEquals(sizeBefore - 1, products.size());
            assertFalse(products.stream().anyMatch(p -> p.getIdProduct().equals(2L)));
        }
    }

    @Nested
    public class Relationship {
    /***
    * !detached entity passed to persist     
    * * Error que ocurre en Hibernate cuando se intenta persistir una entidad en estado "detached",
    * * debido a la falta de cascada de ambos lados de la entidad y de un fetch,     
    * * como una solución temporal se puede realizar lo siguiente     
    * ? @Test          
        void saveWithExistsCategories(){
            Category cat1 = new Category();
            cat1.setIdCategory(1L);
            Category cat2 = new Category();
            cat2.setIdCategory(2L);
            List<Category> categories = new ArrayList<>();
            categories.add(cat1);
            categories.add(cat2);
                
            Product prodNew = new Product("Ikkitousen 20","Ivrea",23,4800f
                    ,LocalDate.of(2000, 4, 26)
                    ,categories);

    * ! Vinculamos las entidades al entity manager y las cambiamos de estado(deja detached)
    * ! al traerlas completas del repo con find.
            List<Category> existingCategories = prodNew.getCategories().stream()
                                                .map(c -> cr.findById(c.getIdCategory()).orElseThrow())
                                                .collect(Collectors.toList());
            prodNew.setCategories(existingCategories);   


            Product prodSaved = pr.save(prodNew);

            assertNotNull(prodSaved.getIdProduct());
            assertEquals(prodSaved.getName(),prodNew.getName());
            assertEquals(prodSaved.getBrand(),prodNew.getBrand());
            assertEquals(prodSaved.getUnitPrice(),prodNew.getUnitPrice());            

            
            Optional<Product> productFind = pr.findById(prodSaved.getIdProduct());
            
            assertEquals(productFind.orElseThrow(),prodSaved);
            assertFalse(productFind.orElseThrow().getCategories().isEmpty());
            
            System.out.println("---Product---" + productFind.orElseThrow().toString() + "\n---Categories---");
            productFind.orElseThrow().getCategories().forEach(System.out::println);
            }
    */    


        @Test
        void findAllWithCategories(){
            List<Product> products = pr.findAll();

            assertFalse(products.isEmpty());
            assertFalse(products.stream()
                .anyMatch(p -> p.getCategories().isEmpty()));

            products.forEach(System.out :: println);    
        }   


        @Test
        void saveWithCategoriesNew(){
            Category cat1 = new Category();
            cat1.setName("Original Soundtrack");
            Category cat2 = new Category();
            cat2.setName("Music Book");

            List<Category> categories = new ArrayList<>();
            categories.add(cat1);
            categories.add(cat2);

            Product prodNew = new Product("Cyberpunk: Edgerunners Soundtrack","Studio Trigger"
                ,4,7900f,LocalDate.of(2022, 9, 13),categories);
            System.out.println("---PRODUCT BEFORE SAVED---" + prodNew.toString());    

            Product prodSaved = pr.save(prodNew);

            assertNotNull(prodSaved.getIdProduct());
            assertEquals(prodSaved.getName(),prodNew.getName());
            assertEquals(prodSaved.getBrand(),prodNew.getBrand());
            assertEquals(prodSaved.getUnitPrice(),prodNew.getUnitPrice());            
            assertFalse(prodSaved.getCategories().isEmpty());
            assertTrue(prodSaved.getCategories().stream().allMatch(c -> c.getIdCategory() != null));

            System.out.println("---PRODUCT SAVED---" + prodSaved.toString());
        }
    }

}
