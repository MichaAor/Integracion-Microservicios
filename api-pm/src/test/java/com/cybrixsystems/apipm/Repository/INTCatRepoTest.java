package com.cybrixsystems.apipm.Repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.jdbc.Sql;

import com.cybrixsystems.apipm.Model.Category;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@Sql("/impCategory.sql")
public class INTCatRepoTest {
    @Autowired
    private CategoryRepository cr;

    @Test
    void findAll_test(){
        List<Category> categories = cr.findAll();

        assertFalse(categories.isEmpty());
        assertEquals(4,  categories.size());
    }

    @Test
    void findById_test(){
        Optional<Category> category = cr.findById(1L);

        assertTrue(category.isPresent());
        assertEquals("Manga",category.orElseThrow().getName());
    }

    @Test
    void findByName_test(){
        Optional<Category> category = cr.findByName("Book");

        assertTrue(category.isPresent());
        assertEquals(3L,category.orElseThrow().getIdCategory());
        assertEquals("Book",category.orElseThrow().getName());
    }

    @Test
    void findByName_ThrowException_test(){
        Optional<Category> category = cr.findByName("Manhwa");

        assertThrows(NoSuchElementException.class, category::orElseThrow);
        assertFalse(category.isPresent());
    }


    @Test
    void save_test(){
        Category categoryS = new Category("Manhwa");
        
        Category category = cr.save(categoryS);
        //Category category = cr.findByName(save.getName()).orElseThrow();

        assertEquals("Manhwa",category.getName());
        //assertEquals(4,category.getIdCategory());   
    }

    @Test
    void update_test(){
        Category categoryS = new Category("Manhwa");
        
        Category category = cr.save(categoryS);

        assertEquals("Manhwa",category.getName());

        category.setName("Visual Novel");
        Category categoryUpd = cr.save(category);

        assertEquals("Visual Novel",categoryUpd.getName());
    }

    @Test
    void delete_test(){
        Category category = cr.findById(2L).orElseThrow();
        assertEquals("Comic", category.getName());

        cr.delete(category);

        assertThrows(NoSuchElementException.class,()->{
            cr.findByName("Comic").orElseThrow();
        });
        assertEquals(3,cr.findAll().size());
    }
}