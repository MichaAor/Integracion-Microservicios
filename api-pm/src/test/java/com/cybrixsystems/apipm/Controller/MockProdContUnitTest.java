package com.cybrixsystems.apipm.Controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cybrixsystems.apipm.Data;
import com.cybrixsystems.apipm.Model.Product;
import com.cybrixsystems.apipm.Service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

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

    @Test
    void findByIdProducts_test() throws Exception{
        Long idFind = 1L;
        when(ps.findProductById(idFind)).thenReturn(Optional.of(Data.PRODUCT));

        mvc.perform(get("/apiPM/Products/"+idFind.toString())
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Saint Seiya Episode G 12"));
        verify(ps).findProductById(idFind);        
    }

    @Test
    void findByNameProducts_test() throws Exception{
        String nameFind = "Saint Seiya Episode G 12";
        when(ps.findProductByName(nameFind)).thenReturn(Optional.of(Data.PRODUCT));

        mvc.perform(get("/apiPM/Products/"+nameFind)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idProduct").value(1))
                .andExpect(jsonPath("$.brand").value("Ivrea"))
                .andExpect(jsonPath("$.stock").value(15));

        verify(ps).findProductByName(nameFind);        
    }

    @Test
    void saveProduct_test() throws JsonProcessingException, Exception{
        Product prodToSave  = Data.PRODUCT_TO_SAVE;

        when(ps.saveORupdateProduct(any())).then(i ->{
            Product p = i.getArgument(0);
            p.setIdProduct(6L);
            return p;
        });

         mvc.perform(post("/apiPM/Products")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(prodToSave)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.idProduct",is(6)))
            .andExpect(jsonPath("$.name",is("Rurōni Kenshin")));

        verify(ps).saveORupdateProduct(any());    
    }

    @Test
    void deleteProduct_test() throws Exception{
        Long idDel = 2L;

        when(ps.deleteProductById(idDel)).thenReturn(true);

        mvc.perform(delete("/apiPM/Products/"+idDel))
            .andExpect(status().is(204));

        verify(ps).deleteProductById(idDel);
    }
}
