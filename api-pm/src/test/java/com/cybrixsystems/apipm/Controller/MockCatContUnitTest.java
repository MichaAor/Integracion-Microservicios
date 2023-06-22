package com.cybrixsystems.apipm.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.cybrixsystems.apipm.Data;
import com.cybrixsystems.apipm.Model.Category;
import com.cybrixsystems.apipm.Service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CategoryController.class)
public class MockCatContUnitTest {
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private CategoryService cs;

    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new ObjectMapper();
    }

    @Test
    void findByIdCategory_test() throws Exception{
        //given
        when(cs.findCategoryById(1L)).thenReturn(Optional.of(Data.CATEGORY));

        //when
        mvc.perform(get("/apiPM/Categories/1")
                .contentType(MediaType.APPLICATION_JSON))
        //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Manga"));

        verify(cs).findCategoryById(1L);        
    }

    @Test
    void findAllCategories_test() throws Exception{
        when(cs.findAllCategories()).thenReturn(Data.CATEGORIES);

        mvc.perform(get("/apiPM/Categories")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$",hasSize(3)))
        .andExpect(content().json(mapper.writeValueAsString(Data.CATEGORIES)));
    }

    @Test
    void saveCategory_test() throws Exception{
        Category catToSave = new Category(null,"Manhwa",new ArrayList<>());

        when(cs.saveORupdateCategory(any())).then(i ->{
            Category c = i.getArgument(0);
            c.setIdCategory(4L);
            return c;
        });

        mvc.perform(post("/apiPM/Categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(catToSave)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.idCategory",is(4)))
            .andExpect(jsonPath("$.name",is("Manhwa")));

        verify(cs).saveORupdateCategory(any());    
    }

    @Test
    void deleteByIdCategory_test() throws Exception{
        Long idCategory = 1L;

            when(cs.deleteCategoryById(idCategory)).thenReturn(true);

            mvc.perform(delete("/apiPM/Categories/"+idCategory))
            .andExpect(status().is(204));

            verify(cs).deleteCategoryById(idCategory);
       }
    }
