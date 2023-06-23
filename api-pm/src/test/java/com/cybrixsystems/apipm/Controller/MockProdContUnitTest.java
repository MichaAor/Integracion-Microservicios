package com.cybrixsystems.apipm.Controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cybrixsystems.apipm.Data;
import com.cybrixsystems.apipm.Service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@WebMvcTest(ProductController.class)
public class MockProdContUnitTest {
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private ProductService ps;

    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void findAllProducts_test() throws Exception{
        when(ps.findAllProducts()).thenReturn(Data.PRODUCTS);

        mvc.perform(get("/apiPM/Products")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$",hasSize(5)));

        verify(ps).findAllProducts();    
    }
}
