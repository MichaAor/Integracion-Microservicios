package com.cybrixsystems.apipm.Repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
// import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.annotation.DirtiesContext;

import com.cybrixsystems.apipm.Model.Category;
import com.cybrixsystems.apipm.Model.Product;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// @Sql("/impProduct.sql")
@Sql("/import.sql")
public class INTProdRepoTest {
    @Autowired
    private ProductRepository pr;

    // @Autowired
    // private CategoryRepository cr;

    @Autowired
    private EntityManager em;

    @Nested
    public class BasicTests {
        @Test
        void findAll_test() {
            List<Product> products = pr.findAll();
            assertFalse(products.isEmpty());
        }

        @Test
        void findById_test() {
            Optional<Product> product = pr.findById(2L);

            assertTrue(product.isPresent());
            assertFalse(product.isEmpty());
            assertEquals(2L, product.orElseThrow().getIdProduct());
            assertEquals("Spiderman - Back in black 1", product.orElseThrow().getName());
            assertEquals("Marvel", product.orElseThrow().getBrand());
            assertEquals(10, product.orElseThrow().getStock());
            assertEquals(5700f, product.orElseThrow().getUnitPrice());
            assertEquals("2007-04-20", product.orElseThrow().getReleaseDate().toString());
        }

        @Test
        void findByName_test() {
            Optional<Product> product = pr.findByName("Spiderman - Back in black 1");

            assertTrue(product.isPresent());
            assertFalse(product.isEmpty());
            assertEquals(2L, product.orElseThrow().getIdProduct());
            assertEquals("Spiderman - Back in black 1", product.orElseThrow().getName());
            assertEquals("Marvel", product.orElseThrow().getBrand());
            assertEquals(10, product.orElseThrow().getStock());
            assertEquals(5700f, product.orElseThrow().getUnitPrice());
            assertEquals("2007-04-20", product.orElseThrow().getReleaseDate().toString());
        }

        @Test
        void deleteById_test() {
            int sizeBefore = pr.findAll().size();
            pr.deleteById(2L);
            List<Product> products = pr.findAll();

            assertFalse(products.isEmpty());
            assertEquals(sizeBefore - 1, products.size());
            assertFalse(products.stream().anyMatch(p -> p.getIdProduct().equals(2L)));
        }
    }

    @Nested
    public class Relationship {
        /*
         !detached entity passed to persist
         * Error que ocurre en Hibernate cuando se intenta persistir una entidad en estado "detached",
         * debido a la falta de cascada de ambos lados de la entidad y de un fetch,
         * como una solución temporal se puede realizar lo siguiente
         ? @Test
         * void saveWithExistsCategories(){
         * Category cat1 = new Category();
         * cat1.setIdCategory(1L);
         * Category cat2 = new Category();
         * cat2.setIdCategory(2L);
         * List<Category> categories = new ArrayList<>();
         * categories.add(cat1);
         * categories.add(cat2);
         * 
         * Product prodNew = new Product("Ikkitousen 20","Ivrea",23,4800f
         * ,LocalDate.of(2000, 4, 26)
         * ,categories);
         * 
         ! Vinculamos las entidades al entity manager y las cambiamos de estado(deja detached)
         ! al traerlas completas del repo con find.
         * List<Category> existingCategories = prodNew.getCategories().stream()
         * .map(c -> cr.findById(c.getIdCategory()).orElseThrow())
         * .collect(Collectors.toList());
         * prodNew.setCategories(existingCategories);
         * 
         * Product prodSaved = pr.save(prodNew);
         * 
         * assertNotNull(prodSaved.getIdProduct());
         * assertEquals(prodSaved.getName(),prodNew.getName());
         * assertEquals(prodSaved.getBrand(),prodNew.getBrand());
         * assertEquals(prodSaved.getUnitPrice(),prodNew.getUnitPrice());
         * 
         * Optional<Product> productFind = pr.findById(prodSaved.getIdProduct());
         * 
         * assertEquals(productFind.orElseThrow(),prodSaved);
         * assertFalse(productFind.orElseThrow().getCategories().isEmpty());
         * 
         * System.out.println("---Product---" + productFind.orElseThrow().toString() +
         * "\n---Categories---");
         * productFind.orElseThrow().getCategories().forEach(System.out::println);
         * }
         */

        @Test
        void findAllWithCategories_test() {
            List<Product> products = pr.findAll();

            assertFalse(products.isEmpty());
            assertFalse(products.stream()
                    .anyMatch(p -> p.getCategories().isEmpty()));

            products.forEach(System.out::println);
        }

        @Test
        void findAllByCategoriesName_test() {
            List<Product> products = pr.findAllByCategoriesName("Manga");

            assertFalse(products.isEmpty());
            assertFalse(products.stream()
                    .allMatch(p -> p.getCategories().stream()
                            .allMatch(c -> c.getName().compareTo("Manga") == 0)));
            products.forEach(System.out::println);
        }

        /*
         ? Searchbar en desarrollo, por ahora se utilizar contemplaciones jpa, es posible usar query.
         ? El nombre del método queda muy grande, incluso agregando los ignoreCase.
         */
        @Test
        void findAllSearchbar_test(){
            String search = "Man";
            List<Product> products = pr.findAllByNameContainingOrBrandContainingOrCategoriesNameContaining(search,search,search);

            assertFalse(products.isEmpty());
            products.forEach(System.out :: println);
        }

        @Test
        void findAllSearchbarQuery_test(){
            String search = "man";
            List<Product> products = pr.findAllBySearch(search);

            assertFalse(products.isEmpty());
            products.forEach(System.out :: println);
        }

        @Test
        void findByIdWithCategories_test() {
            Optional<Product> product = pr.findById(4L);

            assertTrue(product.isPresent());
            assertFalse(product.isEmpty());
            assertEquals(4L, product.orElseThrow().getIdProduct());
            assertEquals("Rurouni Kenshin Profiles", product.orElseThrow().getName());
            assertEquals("VIZ", product.orElseThrow().getBrand());
            assertEquals(2, product.orElseThrow().getStock());
            assertEquals(15900f, product.orElseThrow().getUnitPrice());
            assertEquals("2005-11-01", product.orElseThrow().getReleaseDate().toString());
            assertFalse(product.orElseThrow().getCategories().isEmpty());
            assertTrue(product.orElseThrow().getCategories().stream()
                    .allMatch(c -> c.getIdCategory() != null));
        }

        @Test
        void findByIdWithCategories_notFound_test() {
            Optional<Product> product = pr.findById(9L);

            assertFalse(product.isPresent());
            assertTrue(product.isEmpty());
        }

        @Test
        void saveWithCategoriesNew_test() {
            Category cat1 = new Category();
            cat1.setName("Original Soundtrack");
            Category cat2 = new Category();
            cat2.setName("Music Book");

            List<Category> categories = new ArrayList<>();
            categories.add(cat1);
            categories.add(cat2);

            Product prodNew = new Product("Cyberpunk: Edgerunners Soundtrack", "Studio Trigger", 4, 7900f,
                    LocalDate.of(2022, 9, 13), categories);
            System.out.println("---PRODUCT BEFORE SAVED---" + prodNew.toString());

            Product prodSaved = pr.save(prodNew);

            assertNotNull(prodSaved.getIdProduct());
            assertEquals(prodSaved.getName(), prodNew.getName());
            assertEquals(prodSaved.getBrand(), prodNew.getBrand());
            assertEquals(prodSaved.getUnitPrice(), prodNew.getUnitPrice());
            assertFalse(prodSaved.getCategories().isEmpty());
            assertTrue(prodSaved.getCategories().stream().allMatch(c -> c.getIdCategory() != null));

            System.out.println("---PRODUCT SAVED---" + prodSaved.toString());
        }

        @Test
        void saveWithCategoriesExists_test() {
            List<Long> catsIDs = Arrays.asList(3L, 4L);

            List<Category> categories = catsIDs.stream()
                    .map(id -> em.getReference(Category.class, id))
                    .collect(Collectors.toList());

            Product prodNew = new Product("BIOSHOCK: RAPTURE", "TimunMas", 9, 5200f, LocalDate.of(2012, 03, 20),
                    categories);
            System.out.println("---PRODUCT BEFORE SAVED---" + prodNew.toString());

            Product prodSaved = pr.save(prodNew);

            assertNotNull(prodSaved.getIdProduct());
            assertEquals(prodSaved.getName(), prodNew.getName());
            assertEquals(prodSaved.getBrand(), prodNew.getBrand());
            assertEquals(prodSaved.getUnitPrice(), prodNew.getUnitPrice());
            assertFalse(prodSaved.getCategories().isEmpty());
            assertTrue(prodSaved.getCategories().stream().allMatch(c -> c.getIdCategory() != null));

            System.out.println("---PRODUCT SAVED---" + prodSaved.toString());
        }

        @Test
        void updateCategories_test(){
            Optional<Product> prodToUpd = pr.findById(4L);
            
            assertTrue(prodToUpd.isPresent());
            
            int catSizeBefore = prodToUpd.orElseThrow().getCategories().size();
            System.out.println("---PRODUCT BEFORE UPDATED---" + prodToUpd.orElseThrow().toString());

            prodToUpd.orElseThrow().remCategory(1L);
            Product prodUpdated = pr.save(prodToUpd.orElseThrow());

            int catSizeAfter = prodUpdated.getCategories().size();
            System.out.println("---PRODUCT UPDATED---" + prodUpdated.toString());

            assertEquals(prodToUpd.orElseThrow().getIdProduct(), prodUpdated.getIdProduct());
            assertTrue(prodUpdated.getName().compareTo(prodToUpd.orElseThrow().getName()) == 0);
            assertTrue(prodUpdated.getBrand().compareTo(prodToUpd.orElseThrow().getBrand()) == 0);
            assertNotEquals(catSizeBefore,catSizeAfter);
            assertFalse(prodUpdated.getCategories().stream()
                        .allMatch(c -> c.getName().compareTo("Manga") == 0));
        }

        @Test
        void deleteById_test() {
/*
 * A diferencia de Category, no se requiere de desvinculación y se realizar el delete sin problemas.
 * Testeado con todas los registros disponibles en import.sql
 */
            int sizeBefore = pr.findAll().size();
            Long idDel = 4L;
            pr.deleteById(idDel);

            Optional<Product> prodDel = pr.findById(idDel);

            assertTrue(prodDel.isEmpty());
            assertFalse(prodDel.isPresent());

            List<Product> products = pr.findAll();

            assertNotEquals(sizeBefore, products.size());
            products.forEach(System.out::println);
        }
    }
}
