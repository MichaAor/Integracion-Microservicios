package com.cybrixsystems.apipm.Repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
import com.cybrixsystems.apipm.Model.Product;
import com.cybrixsystems.apipm.Service.ProductService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class MockProdRepoUnitTest {
    @MockBean
    ProductRepository pr;

    @Autowired
    ProductService ps;

    @Nested
    public class FindAllTestsNested {
       @Test
       @Order(1)
       void findAll_Test(){
           when(pr.findAll()).thenReturn(Data.PRODUCTS);

           List<Product> products = ps.findAllProducts();

           assertFalse(products.isEmpty());
           assertEquals(5,products.size());
           verify(pr).findAll();
       }

        // @Test
        // @Order(2)
        // void findAllWithCategories_Test(){
        // when(pr.findAll()).thenReturn(Data.PRODUCTS_CATEGORIES);
        //
        // List<Product> products = ps.findAllProducts();
        //
        // assertFalse(products.isEmpty());
        // assertEquals(5,products.size());
        // assertFalse(products.get(2).getCategories().isEmpty());
        // assertEquals(1,products.get(2).getCategories().size());
        // verify(pr).findAll();
        // }

       @Test
       @Order(3)
       void findAllEmpty_Test(){
           when(pr.findAll()).thenReturn(Collections.emptyList());

           List<Product> products = ps.findAllProducts();

           assertTrue(products.isEmpty());
           assertEquals(0,products.size());
       }
    }

    @Nested
    public class FindByTestsNested {
       @Test
       @Order(4)
       void findById_Test(){
           when(pr.findById(1L)).thenReturn(Optional.of(Data.PRODUCT));

           Optional<Product> optProd = ps.findProductById(1L);

           assertTrue(optProd.isPresent());
           assertEquals(1L, optProd.get().getIdProduct());
           assertEquals("Saint Seiya Episode G 12", optProd.get().getName());
           verify(pr).findById(any());
       }

       @Test
       @Order(5)
       void findByIdEmpty_Test(){
           when(pr.findById(7L)).thenReturn(Optional.empty());

           Optional<Product> optCat = ps.findProductById(7L);

           assertFalse(optCat.isPresent());
           assertTrue(optCat.isEmpty());
           verify(pr).findById(any());
       }

       @Test
       @Order(6)
       void findByName_Test(){
           when(pr.findByName("Saint Seiya Episode G 12")).thenReturn(Optional.of(Data.PRODUCT));

           Optional<Product> optProd = ps.findProductByName("Saint Seiya Episode G 12");

           assertTrue(optProd.isPresent());
           assertEquals(1L, optProd.get().getIdProduct());
           assertEquals("Saint Seiya Episode G 12", optProd.get().getName());
           verify(pr).findByName("Saint Seiya Episode G 12");
       }

       @Test
       @Order(7)
       void findByNameEmpty_Test(){
           when(pr.findByName("Saint Seiya ND 20")).thenReturn(Optional.empty());

           Optional<Product> optProd = ps.findProductByName("Saint Seiya ND 20");

           assertFalse(optProd.isPresent());
           assertTrue(optProd.isEmpty());
           verify(pr).findByName("Saint Seiya ND 20");
       }
    }

    @Nested
    public class SaveTestsNested {

        @Test
        @Order(8)
        void save_test() {
            // Cuando se invoca el guardar de cualquier examen, le asignamos un id +1.
            // Trabajamos en un entorno impulsado al comportamiento.
            // Given: Precondiciones en el entorno de prueba.
            int size = Data.PRODUCTS.size();
            Product newProd = Data.PRODUCT_TO_SAVE;
            when(pr.save(any(Product.class))).thenAnswer(invocation -> {
                Product prodSaved = invocation.getArgument(0);
                prodSaved.setIdProduct(Long.valueOf(Data.PRODUCTS.size() + 1));
                Data.PRODUCTS.add(prodSaved);
                return prodSaved;
            });

            // When: ejecuta aquí el entorno de prueba.
            Product prodSaved = ps.save(newProd);

            // Then: Valida entorno de prueba.
            assertNotNull(prodSaved.getIdProduct());
            assertEquals(newProd.getName(), prodSaved.getName());
            assertEquals(newProd.getBrand(), prodSaved.getBrand());
            assertEquals(newProd.getReleaseDate(), prodSaved.getReleaseDate());
            assertEquals(newProd.getStock(), prodSaved.getStock());
            assertEquals(newProd.getUnitPrice(), prodSaved.getUnitPrice());

            assertEquals(size + 1, Data.PRODUCTS.size());
            assertTrue(Data.PRODUCTS.stream().anyMatch(p -> p.getName().equals(newProd.getName())));

            verify(pr).save(any(Product.class));
        }

        @Test
        @Order(9)
        void doAnswerSave_test() {
            // Cuando se invoca el guardar de cualquier examen, le asignamos un id +1.
            // Trabajamos en un entorno impulsado al comportamiento.
            Product newProd = Data.PRODUCT_TO_SAVE;
            int size = Data.PRODUCTS.size();

            // Given: Precondiciones en el entorno de prueba.
            doAnswer(new Answer<Product>() {
                @Override
                public Product answer(InvocationOnMock invocationOnMock) {
                    Product prodSaved = invocationOnMock.getArgument(0);
                    prodSaved.setIdProduct(Long.valueOf(Data.CATEGORIES.size() + 1));
                    Data.PRODUCTS.add(prodSaved);
                    return prodSaved;
                }
            }).when(pr).save(any(Product.class));

            // When: ejecuta aquí el entorno de prueba.
            Product prodSaved = ps.save(newProd);

            // Then: Valida entorno de prueba.
            assertNotNull(prodSaved.getIdProduct());
            assertEquals(newProd.getName(), prodSaved.getName());
            assertEquals(newProd.getBrand(), prodSaved.getBrand());
            assertEquals(newProd.getReleaseDate(), prodSaved.getReleaseDate());
            assertEquals(newProd.getStock(), prodSaved.getStock());
            assertEquals(newProd.getUnitPrice(), prodSaved.getUnitPrice());

            assertEquals(size + 1, Data.PRODUCTS.size());
            assertTrue(Data.PRODUCTS.stream().anyMatch(p -> p.getName().equals(newProd.getName())));

            verify(pr).save(any(Product.class));
        }

        // @Test
        // @Order(10)
        // void saveWithProducts_test(){
        // Product newProd = Data.PRODUCT_TO_SAVE;
        // newProd.addCategory(Data.CATEGORY);
        //
        // doCallRealMethod().when(pr).save(newProd);
        //
        // Product saveProd = ps.save(newProd);
        //
        // newProd.setIdProduct(6L);
        //
        // assertNotNull(saveProd.getIdProduct());
        // assertEquals(6L,saveProd.getIdProduct());
        // assertEquals(newProd,saveProd);
        //
        // verify(pr).save(any(Product.class));
        // }
    }

    @Nested
    public class DeleteTestsNested {
        @Test
        @Order(11)
        void deleteById_test() {
            Long idProduct = 2L;
            int size = Data.PRODUCTS.size();

            doAnswer(invocation ->{
                Long id = invocation.getArgument(0);
                Data.PRODUCTS.removeIf(p -> p.getIdProduct().equals(id));
            return null;    
            }).when(pr).deleteById(idProduct);

            ps.deleteProductById(idProduct);

            assertEquals(size - 1, Data.PRODUCTS.size());
            assertFalse(ps.findProductById(idProduct).isPresent());

            verify(pr).deleteById(idProduct);
        }

        @Test
        @Order(12)
        void deleteByIdErrorInput_test() {
            Long idProduct = 8L;
            int size = Data.PRODUCTS.size();

            doAnswer(invocation ->{
                Long id = invocation.getArgument(0);
                Data.PRODUCTS.removeIf(p -> p.getIdProduct().equals(id));
            return null;    
            }).when(pr).deleteById(idProduct);

            ps.deleteProductById(idProduct);

            assertEquals(size, Data.PRODUCTS.size());
            assertFalse(ps.findProductById(idProduct).isPresent());

            verify(pr).deleteById(idProduct);
        }
    }

}
