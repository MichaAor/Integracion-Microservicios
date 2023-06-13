package com.cybrixsystems.apipm.Repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cybrixsystems.apipm.Data;
import com.cybrixsystems.apipm.Model.Category;
import com.cybrixsystems.apipm.Service.CategoryService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class MockCatRepoUnitTest {
    @MockBean
    CategoryRepository cr;

    @Autowired
    CategoryService cs;

    @Nested
    public class FindAllTestsNested {
       @Test
       @Order(1)
       void findAll_Test(){
           when(cr.findAll()).thenReturn(Data.CATEGORIES);

           List<Category> categories = cs.findAllCategories();

           assertFalse(categories.isEmpty());
           assertEquals(3,categories.size());
           verify(cr).findAll();
       }

       @Test
       @Order(2)
       void findAllEmpty_Test(){
           when(cr.findAll()).thenReturn(Collections.emptyList());

           List<Category> categories = cs.findAllCategories();

           assertTrue(categories.isEmpty());
           assertEquals(0,categories.size());
       }
    }

    @Nested
    public class FindByTestsNested {
       @Test
       @Order(3)
       void findById_Test(){
           when(cr.findById(anyLong())).thenReturn(Optional.of(Data.CATEGORY));

           Optional<Category> optCat = cs.findCategoryById(1L);

           assertTrue(optCat.isPresent());
           assertEquals(1L, optCat.get().getIdCategory());
           assertEquals("Manga", optCat.get().getName());
           verify(cr).findById(any());
       }

       @Test
       @Order(4)
       void findByIdEmpty_Test(){
           when(cr.findById(7L)).thenReturn(Optional.empty());

           Optional<Category> optCat = cs.findCategoryById(7L);

           assertFalse(optCat.isPresent());
           assertTrue(optCat.isEmpty());
           verify(cr).findById(any());
       }

       @Test
       @Order(5)
       void findByName_Test(){
           when(cr.findByName("Manga")).thenReturn(Optional.of(Data.CATEGORY));

           Optional<Category> optCat = cs.findCategoryByName("Manga");

           assertTrue(optCat.isPresent());
           assertEquals(1L, optCat.get().getIdCategory());
           assertEquals("Manga", optCat.get().getName());
           verify(cr).findByName("Manga");
       }

       @Test
       @Order(6)
       void findByNameEmpty_Test(){
           when(cr.findByName("Novel")).thenReturn(Optional.empty());

           Optional<Category> optCat = cs.findCategoryByName("Novel");

           assertFalse(optCat.isPresent());
           assertTrue(optCat.isEmpty());
           verify(cr).findByName("Novel");
       }
    }

    @Nested
    public class SaveTestsNested {
        @Test
        @Order(7)
        void save_test() {
            // Cuando se invoca el guardar de cualquier examen, le asignamos un id +1.
            // Trabajamos en un entorno impulsado al comportamiento.
            Category newCat = new Category("Manhwa");

            // Given: Precondiciones en el entorno de prueba.
            when(cr.save(any(Category.class))).then(new Answer<Category>() {
                Long sequence = 4L;

                @Override
                public Category answer(InvocationOnMock invocationOnMock) {
                    Category category = invocationOnMock.getArgument(0);
                    category.setIdCategory(sequence++);
                    return category;
                }
            });

            // When: ejecuta aquí el entorno de prueba.
            Category category = cs.save(newCat);

            // Then: Valida entorno de prueba.
            assertNotNull(category.getIdCategory());
            assertEquals(4L, category.getIdCategory());
            assertEquals("Manhwa", category.getName());

            verify(cr).save(any(Category.class));
        }

        @Test
        @Order(8)
        void doAnswerSave_test() {
            Category newCat = Data.CATEGORY_TO_SAVE;

            doAnswer(new Answer<Category>() {
                Long sequence = 4L;

                @Override
                public Category answer(InvocationOnMock invocationOnMock) {
                    Category category = invocationOnMock.getArgument(0);
                    category.setIdCategory(sequence++);
                    return category;
                }
            }).when(cr).save(any(Category.class));

            Category category = cs.save(newCat);

            assertNotNull(category.getIdCategory());
            assertEquals(4L, category.getIdCategory());
            assertEquals("Manhwa", category.getName());

            verify(cr).save(any(Category.class));
        }
    }

    @Nested
    public class DeleteTestsNested {
        @Test
        @Order(9)
        void deleteById_test() {
            Long idCategory = 2L;
            int size = Data.CATEGORIES.size();

            doAnswer(invocation ->{
                Long id = invocation.getArgument(0);
                Data.CATEGORIES.removeIf(c -> c.getIdCategory().equals(id));
            return null;    
            }).when(cr).deleteById(idCategory);

            cs.deleteCategoryById(idCategory);

            assertEquals(size -1, Data.CATEGORIES.size());
            assertFalse(cs.findCategoryById(idCategory).isPresent());

            verify(cr).deleteById(idCategory);
        }

       @Test
       @Order(10)
       void deleteByIdErrorInput_test(){
           Long idCategory = 9L;
           int size = Data.CATEGORIES.size();

            doAnswer(invocation ->{
                Long id = invocation.getArgument(0);
                Data.CATEGORIES.removeIf(c -> c.getIdCategory().equals(id));
            return null;    
            }).when(cr).deleteById(idCategory);

            cs.deleteCategoryById(idCategory);

            assertEquals(size, Data.CATEGORIES.size());
            assertFalse(cs.findCategoryById(idCategory).isPresent());

            verify(cr).deleteById(idCategory);
            verify(cr).findById(idCategory);
       }
    }
}
