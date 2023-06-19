package com.cybrixsystems.apipm.Controller;

import static org.mockito.Mockito.*;

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

import com.cybrixsystems.apipm.Data;
import com.cybrixsystems.apipm.Model.Category;
import com.cybrixsystems.apipm.Service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // @Test
    // void saveCategory_test() throws JsonProcessingException, Exception{
    //     Category catToSave = Data.CATEGORY_TO_SAVE;

    //     mvc.perform(post("/apiPM/Categories").contentType(MediaType.APPLICATION_JSON)
    //     .content(mapper.writeValueAsString(catToSave)))

    //     .andExpect(status().isCreated())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //         .andExpect(jsonPath("$.idCategory").value(1L))
    //         .andExpect(jsonPath("$.name").value("Manhwa"))
    //         .andExpect(jsonPath("$.products").isEmpty());
    // }
}
