package com.cybrixsystems.apipm.Controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.cybrixsystems.apipm.Data;
import com.cybrixsystems.apipm.Model.Category;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/import.sql")
public class CatContWebTestClientTest {
    @Autowired
    private WebTestClient client; 

    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void findAllCategories_test(){
        client.get().uri("/apiPM/Categories").exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody(Category[].class)
            .consumeWith(response -> {
               List<Category> categories = Arrays.asList(response.getResponseBody());
               assertEquals(4, categories.size());
               assertTrue(categories.stream().allMatch(c -> !c.getProducts().isEmpty()));
            });
    }

    @Test
    @Order(2)
    void findAllCategories_test2(){
        client.get().uri("/apiPM/Categories").exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(Category.class)
            .consumeWith(response -> {
               List<Category> categories = response.getResponseBody();
               assertEquals(4, categories.size());
               assertTrue(categories.stream().allMatch(c -> !c.getProducts().isEmpty()));
            }).hasSize(4);
    }


    @Test
    @Order(3)
    void findByIdCategory_JSONtest(){
        Long idFind = 3L;
        client.get().uri("/apiPM/Categories/"+idFind).exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
                .jsonPath("$.idCategory").isEqualTo(idFind)
                .jsonPath("$.name").isEqualTo("Novel")
                .jsonPath("$.products",hasSize(1));
    }

    @Test
    @Order(4)
    void findByIdCategory_JSONtest2(){
        Long idFind = 3L;
        client.get().uri("/apiPM/Categories/"+idFind).exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
                .consumeWith(response -> {
                    try {
                        JsonNode json = mapper.readTree(response.getResponseBody());
                            assertEquals(idFind.toString(), json.path("idCategory").asText());
                            assertEquals("Novel", json.path("name").asText());
                            assertEquals(1, json.path("products").size());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(5)
    void findByIdCategory_test(){
        Long idFind = 3L;
        client.get().uri("/apiPM/Categories/"+idFind).exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(Category.class)
                .consumeWith(response -> {
                    Category catFound = response.getResponseBody();
                    assertEquals(3L, catFound.getIdCategory());
                    assertEquals("Novel", catFound.getName());
                    assertFalse(catFound.getProducts().isEmpty());
                    assertEquals(1,catFound.getProducts().size());
                });
    }

    @Test
    @Order(6)
    void saveCategory_test(){
        Category catToSave = Data.CATEGORY_TO_SAVE;

        client.post().uri("/apiPM/Categories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(catToSave)
            .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                    .jsonPath("$.idCategory").isEqualTo(5L)
                    .jsonPath("$.name").isEqualTo("Manhwa")
                    .jsonPath("$.products").isEmpty();
    }

    @Test
    @Order(7)
    void saveCategory_test2(){
        Category catToSave = Data.CATEGORY_TO_SAVE;

        client.post().uri("/apiPM/Categories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(catToSave)
            .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Category.class)
                    .consumeWith(r -> {
                        Category catSaved = r.getResponseBody();
                        assertNotNull(catSaved);
                        assertNotNull(catSaved.getIdCategory());
                        assertEquals(catToSave.getName(), catSaved.getName());
                        assertTrue(catSaved.getProducts().isEmpty());
                    });
    }

    @Test
    @Order(8)
    void deleteByIdCategory(){
        Long idDel = 3L;

        client.delete().uri("/apiPM/Categories/"+idDel)
            .exchange()
            .expectStatus().isNoContent();
    }
}
