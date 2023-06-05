package com.cybrixsystems.apipm.Unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.cybrixsystems.apipm.Data;
import com.cybrixsystems.apipm.Model.Category;
import com.cybrixsystems.apipm.Repository.IMPCategoryRepository;
import com.cybrixsystems.apipm.Service.IMPCategoryService;

@ExtendWith(MockitoExtension.class)
public class IMPCatRepoTest {
    @Mock
    IMPCategoryRepository cr;

    @InjectMocks
    IMPCategoryService cs;

    @Test
    void findAll_Test(){
        when(cr.findAll()).thenReturn(Data.CATEGORIES);

        List<Category> categories = cs.findAllCategories();

        assertFalse(categories.isEmpty());
        assertEquals(3,categories.size());
        verify(cr).findAll();
    }

    @Test
    void findAllEmpty_Test(){
        when(cr.findAll()).thenReturn(Collections.emptyList());

        List<Category> categories = cs.findAllCategories();

        assertTrue(categories.isEmpty());
        assertEquals(0,categories.size());
    }

    @Test
    void findById_Test(){
        when(cr.findById(anyLong())).thenReturn(Optional.of(Data.CATEGORY));

        Optional<Category> optCat = cs.findCategoryById(1L);

        assertTrue(optCat.isPresent());
        assertEquals(1L, optCat.get().getIdCategory());
        assertEquals("Manga", optCat.get().getName());
        verify(cr).findById(any());
    }

    @Test
    void findByIdEmpty_Test(){
        when(cr.findById(7L)).thenReturn(Optional.empty());

        Optional<Category> optCat = cs.findCategoryById(7L);
        
        assertFalse(optCat.isPresent());
        assertTrue(optCat.isEmpty());
        verify(cr).findById(any());
    }

    @Test
    void findByName_Test(){
        when(cr.findByName("Manga")).thenReturn(Optional.of(Data.CATEGORY));

        Optional<Category> optCat = cs.findCategoryByName("Manga");

        assertTrue(optCat.isPresent());
        assertEquals(1L, optCat.get().getIdCategory());
        assertEquals("Manga", optCat.get().getName());
        verify(cr).findByName("Manga");
    }

    @Test
    void findByNameEmpty_Test(){
        when(cr.findByName("Novel")).thenReturn(Optional.empty());

        Optional<Category> optCat = cs.findCategoryByName("Novel");
        
        assertFalse(optCat.isPresent());
        assertTrue(optCat.isEmpty());
        verify(cr).findByName("Novel");
    }

    @Test
    void save_test(){
        //Cuando se invoca el guardar de cualquier examen, le asignamos un id +1.
        //Trabajamos en un entorno impulsado al comportamiento.
        Category newCat = new Category();   newCat.setName("Manhwa");

        //Given: Precondiciones en el entorno de prueba.
        when(cr.save(any(Category.class)))
            .then(new Answer<Category>() {
                Long sequence = 5L;
                @Override
                public Category answer (InvocationOnMock invocationOnMock){
                    Category category = invocationOnMock.getArgument(0);
                    category.setIdCategory(sequence++);
                return category;
                }
            });

            // When: ejecuta aqui el entorno de prueba.
            Category category = cs.save(newCat);

            //Then: Valida entorno de prueba.
            assertNotNull(category.getIdCategory());
            assertEquals(5L,category.getIdCategory());
            assertEquals("Manhwa",category.getName());

            verify(cr).save(any(Category.class));
    }

    @Test
    void saveDoCallRealMethod_test(){
        //Cuando se invoca el guardar de cualquier examen, le asignamos un id +1.
        //Trabajamos en un entorno impulsado al comportamiento.
        Category newCat = new Category();   newCat.setName("Manhwa");

        //Given: Precondiciones en el entorno de prueba.
        doCallRealMethod().when(cr).save(any(Category.class));

            // When: ejecuta aqui el entorno de prueba.
            Category category = cs.save(newCat);

            //Then: Valida entorno de prueba.
            assertNotNull(category.getIdCategory());
            assertEquals(4L,category.getIdCategory());
            assertEquals("Manhwa",category.getName());

            verify(cr).save(any(Category.class));
    }

    @Test
    void doAnswerSave_test(){
        //Cuando se invoca el guardar de cualquier examen, le asignamos un id +1.
        //Trabajamos en un entorno impulsado al comportamiento.
            Category newCat = new Category();   newCat.setName("Manhwa");

            //Given: Precondiciones en el entorno de prueba.
            doAnswer(new Answer<Category>(){
                Long sequence = 5L;
                    @Override
                    public Category answer (InvocationOnMock invocationOnMock){
                        Category category = invocationOnMock.getArgument(0);
                        category.setIdCategory(sequence++);
                    return category;
                    }
            }).when(cr).save(any(Category.class));
       
            // When: ejecuta aqui el entorno de prueba.
            Category category = cs.save(newCat);

            //Then: Valida entorno de prueba.
            assertNotNull(category.getIdCategory());
            assertEquals(5L,category.getIdCategory());
            assertEquals("Manhwa",category.getName());

            verify(cr).save(any(Category.class));
    }

    @Test
    void deleteById_test(){
        doCallRealMethod().when(cr).findAll();
        doCallRealMethod().when(cr).findById(anyLong());
        doCallRealMethod().when(cr).deleteById(anyLong());

        List<Category> categories = cs.findAllCategories();

        assertFalse(categories.isEmpty());
        assertEquals(3,categories.size());
        assertTrue(categories.stream().anyMatch(e -> e.getName().compareTo("Comic")==0));

        cs.deleteCategoryById(2L);

        categories = cs.findAllCategories();

        assertFalse(categories.isEmpty());
        assertEquals(2,categories.size());
        assertFalse(categories.stream().anyMatch(e -> e.getName().compareTo("Comic")==0));
    }


}
