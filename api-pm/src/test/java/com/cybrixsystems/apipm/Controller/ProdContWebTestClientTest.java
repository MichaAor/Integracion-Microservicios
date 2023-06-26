package com.cybrixsystems.apipm.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.cybrixsystems.apipm.Data;
import com.cybrixsystems.apipm.Model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Sql("/import.sql")
public class ProdContWebTestClientTest {
    @Autowired
    WebTestClient client;

    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void findAllProduct_test_test(){
        client.get().uri("/apiPM/Products").exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(Product.class)
                .consumeWith(r ->{
                    List<Product> products = r.getResponseBody();
                    assertNotNull(products);
                    assertFalse(products.isEmpty());
                    assertEquals(4, products.size());
                    assertTrue(products.stream().allMatch(p -> ! p.getCategories().isEmpty()));
                });
    }

    
    @Test
    @Order(2)
    void findByIdProduct_test(){
        Long idFind = 2L;

        client.get().uri("/apiPM/Products/"+idFind).exchange()
            .expectStatus().isOk()
            .expectBody(Product.class)
                .consumeWith(r ->{
                    Product prodFound = r.getResponseBody();
                    assertNotNull(prodFound);
                    assertEquals("Spider-Man - Back in black 1", prodFound.getName());
                    assertEquals("Marvel", prodFound.getBrand());
                    assertEquals(10, prodFound.getStock());
                    assertFalse(prodFound.getCategories().isEmpty());
                });
    }

    @Test
    @Order(3)
    void saveProduct_test(){
        Product prodToSave = Data.PRODUCT_TO_SAVE;

        client.post().uri("/apiPM/Products")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(prodToSave)
                .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(Product.class)
                .consumeWith(r->{
                    Product prodSaved = r.getResponseBody();
                    assertNotNull(prodSaved);
                    assertNotNull(prodSaved.getIdProduct());
                    assertEquals(prodSaved.getName(),prodToSave.getName());
                    assertEquals(prodSaved.getBrand(),prodToSave.getBrand());
                    assertEquals(prodSaved.getStock(),prodToSave.getStock());
                    assertEquals(prodSaved.getReleaseDate(),prodToSave.getReleaseDate());
                    assertTrue(prodSaved.getCategories().isEmpty());
                });  
    }

    @Test
    @Order(4)
    void deleteByIdProduct_test(){
        Long idDel = 3L;
        client.delete().uri("/apiPM/Products/"+idDel)
            .exchange()
        .expectStatus().isNoContent();    
    }


    
}
