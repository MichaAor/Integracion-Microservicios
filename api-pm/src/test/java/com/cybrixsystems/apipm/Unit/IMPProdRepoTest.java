package com.cybrixsystems.apipm.Unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.cybrixsystems.apipm.Data;
import com.cybrixsystems.apipm.Model.Product;
import com.cybrixsystems.apipm.Repository.IMPProductRepository;
import com.cybrixsystems.apipm.Service.IMPProductService;

@ExtendWith(MockitoExtension.class)
public class IMPProdRepoTest {
    @Mock
    IMPProductRepository pr;

    @InjectMocks
    IMPProductService ps;

    @Nested
    public class findAllTestsNested{
        @Test
        @Order(1)
        void findAll_Test(){
            when(pr.findAll()).thenReturn(Data.PRODUCTS);

            List<Product> products = ps.findAllProducts();

            assertFalse(products.isEmpty());
            assertEquals(5,products.size());
            verify(pr).findAll();
        }

        @Test
        @Order(2)
        void findAllWithCategories_Test(){
            when(pr.findAll()).thenReturn(Data.PRODUCTS_CATEGORIES);

            List<Product> products = ps.findAllProducts();

            assertFalse(products.isEmpty());
            assertEquals(5,products.size());
            assertFalse(products.get(2).getCategories().isEmpty());
            assertEquals(1,products.get(2).getCategories().size());
            verify(pr).findAll();
        }
        
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
    public class findByTestsNested{
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
    public class saveTestsNested{

        @Test
        @Order(8)
        void save_Real_test(){
            //Cuando se invoca el guardar de cualquier examen, le asignamos un id +1.
            //Trabajamos en un entorno impulsado al comportamiento.
            Product newProd = Data.PRODUCT_TO_SAVE;

            //Given: Precondiciones en el entorno de prueba.
            doCallRealMethod().when(pr).save(any(Product.class));

                // When: ejecuta aquí el entorno de prueba.
                Product product = ps.save(newProd);

                //Then: Valida entorno de prueba.
                assertNotNull(product.getIdProduct());
                assertEquals(Data.PRODUCT_SAVE.getName(),product.getName());
                assertEquals(Data.PRODUCT_SAVE.getBrand(),product.getBrand());
                assertEquals(Data.PRODUCT_SAVE.getReleaseDate(),product.getReleaseDate());
                assertEquals(Data.PRODUCT_SAVE.getStock(),product.getStock());
                assertEquals(Data.PRODUCT_SAVE.getUnitPrice(),product.getUnitPrice());
                verify(pr).save(any(Product.class));
        }
        
        @Test
        @Order(9)
        void doAnswerSave_test(){
            //Cuando se invoca el guardar de cualquier examen, le asignamos un id +1.
            //Trabajamos en un entorno impulsado al comportamiento.
                Product newProd = Data.PRODUCT_TO_SAVE;

                //Given: Precondiciones en el entorno de prueba.
                doAnswer(new Answer<Product>(){
                    Long sequence = 6L;
                        @Override
                        public Product answer (InvocationOnMock invocationOnMock){
                            Product product = invocationOnMock.getArgument(0);
                            product.setIdProduct(sequence++);
                        return product;
                        }
                }).when(pr).save(any(Product.class));
        
                // When: ejecuta aquí el entorno de prueba.
                Product product = ps.save(newProd);

                //Then: Valida entorno de prueba.
                assertNotNull(product.getIdProduct());
                assertEquals(6L,product.getIdProduct());
                assertEquals("Rurōni Kenshin",product.getName());

                verify(pr).save(any(Product.class));
        }

        @Test
        @Order(10)
        void saveWithProducts_test(){
            Product newProd = Data.PRODUCT_TO_SAVE;
            newProd.addCategory(Data.CATEGORY);  
            
            doCallRealMethod().when(pr).save(newProd);

            Product saveProd = ps.save(newProd);

            newProd.setIdProduct(6L);

            assertNotNull(saveProd.getIdProduct());
            assertEquals(6L,saveProd.getIdProduct());
            assertEquals(newProd,saveProd);

            verify(pr).save(any(Product.class));
        }
    }

    @Nested
    public class deleteTestsNested{
        @Test
        @Order(11)
        void deleteById_test(){
            doCallRealMethod().when(pr).findAll();
            doCallRealMethod().when(pr).findById(anyLong());
            doCallRealMethod().when(pr).deleteById(anyLong());
    
            List<Product> products = ps.findAllProducts();
    
            assertFalse(products.isEmpty());
            assertEquals(5,products.size());
            assertTrue(products.stream().anyMatch(e -> e.getName().compareTo("Spiderman - Back in black 1")==0));
    
            ps.deleteProductById(2L);
    
            assertFalse(products.isEmpty());
            assertEquals(4,products.size());
            assertFalse(products.stream().anyMatch(e -> e.getName().compareTo("Spiderman - Back in black 1")==0));
        }
    
        @Test
        @Order(12)
        void deleteByIdErrorInput_test(){
            doCallRealMethod().when(pr).findAll();
            doCallRealMethod().when(pr).findById(anyLong());
            doCallRealMethod().when(pr).deleteById(anyLong());
    
            List<Product> products = ps.findAllProducts();
    
            assertFalse(products.isEmpty());
            assertEquals(5,products.size());
            assertTrue(products.stream().anyMatch(e -> e.getName().compareTo("Spiderman - Back in black 1")==0));
    
            ps.deleteProductById(7L);
    
            assertFalse(products.isEmpty());
            assertEquals(5,products.size());
        }
    }
    
}
