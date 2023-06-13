package com.cybrixsystems.apipm.Repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import com.cybrixsystems.apipm.Model.Category;
import com.cybrixsystems.apipm.Model.Product;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@Sql("/impCategory.sql")
@Sql("/import.sql")
public class INTCatRepoTest {
    @Autowired
    private CategoryRepository cr;

    @Nested
    public class BasicTest {

        @Test
        void findAll_test() {
            List<Category> categories = cr.findAll();

            assertFalse(categories.isEmpty());
            assertEquals(4, categories.size());
        }

        @Test
        void findById_test() {
            Optional<Category> category = cr.findById(1L);

            assertTrue(category.isPresent());
            assertEquals("Manga", category.orElseThrow().getName());
        }

        @Test
        void findByName_test() {
            Optional<Category> category = cr.findByName("Novel");

            assertTrue(category.isPresent());
            assertEquals(3L, category.orElseThrow().getIdCategory());
            assertEquals("Novel", category.orElseThrow().getName());
        }

        @Test
        void findByName_ThrowException_test() {
            Optional<Category> category = cr.findByName("Manhwa");

            assertThrows(NoSuchElementException.class, category::orElseThrow);
            assertFalse(category.isPresent());
        }

        @Test
        void save_test() {
            Category categoryS = new Category("Manhwa");

            Category category = cr.save(categoryS);
            // Category category = cr.findByName(save.getName()).orElseThrow();

            assertEquals("Manhwa", category.getName());
            // assertEquals(4,category.getIdCategory());
        }

        @Test
        void update_test() {
            Category categoryS = new Category("Manhwa");

            Category category = cr.save(categoryS);

            assertEquals("Manhwa", category.getName());

            category.setName("Visual Novel");
            Category categoryUpd = cr.save(category);

            assertEquals("Visual Novel", categoryUpd.getName());
        }

        @Test
        void delete_test() {
            Category category = cr.findById(2L).orElseThrow();
            assertEquals("Comic", category.getName());

            cr.delete(category);

            assertThrows(NoSuchElementException.class, () -> {
                cr.findByName("Comic").orElseThrow();
            });
            assertEquals(3, cr.findAll().size());
        }
    }

    @Nested
    public class Relationship{

        @Test
        void findAllWithProducts(){
            List<Category> categories = cr.findAll();

            assertFalse(categories.isEmpty());
            assertFalse(categories.stream()
                .anyMatch(p -> p.getProducts().isEmpty()));

            categories.forEach(System.out::println);    
        }

        @Test
        void findByIdWithProducts(){
            Optional<Category> category = cr.findById(3L);

            assertTrue(category.isPresent());
            assertEquals("Novel", category.orElseThrow().getName());
            assertTrue(category.orElseThrow().getProducts().stream().allMatch(p -> p.getIdProduct() != null));  
        }

        @Test
        void saveWithProducts(){
            Product prod1 = new Product("Cyberpunk: Edgerunners Soundtrack","Studio Trigger"
                ,4,7900f,LocalDate.of(2022, 9, 13));
            Product prod2 = new Product("Saint Seiya Eternal CD-Box","Columbia Music Entertainment"
                    ,23,49000f,LocalDate.of(2008, 7, 30));
            List<Product> products = new ArrayList<>();
            products.add(prod1);
            products.add(prod2);

            Category catNew = new Category("Original Soundtrack", products);
            System.out.println("---CATEGORY SAVED---" + catNew.toString());

            Category catSaved = cr.save(catNew);

            assertNotNull(catSaved.getIdCategory());
            assertEquals(catSaved.getName(),catNew.getName());            
            assertTrue(catSaved.getProducts().stream().allMatch(p -> p.getIdProduct() != null));            

            System.out.println("---CATEGORY SAVED---" + catSaved.toString());
        }
    }
}