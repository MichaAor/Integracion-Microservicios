package com.cybrixsystems.apipm.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.*;

// import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;
    private String name;
    private String brand;
    private int stock;
    private float unitPrice;
    private LocalDate releaseDate;
    private LocalDateTime registerDate;
    private LocalDateTime lastModDate;

    /* 
     * cascade=ALL is equivalent to cascade={PERSIST, MERGE, REMOVE, REFRESH, DETACH}. 
     ! En caso de borrar una entidad asociada a otra, se ejecuta la cascada borrando también los
     ! otros productos asociados a la categoría, por lo cual un deleteBy borraría mas que solo 1 entidad.
     ? Como solución, restringir cascade.REMOVE para evitar la propagación del delete y borrar solo 1.
    */
    
    @ManyToMany(fetch = FetchType.LAZY
                ,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
    @JoinTable(name = "ProductCategory", 
        joinColumns = {
            @JoinColumn(name = "idProduct",referencedColumnName = "idProduct")
        }, 
        inverseJoinColumns = {
            @JoinColumn(name = "idCategory",referencedColumnName = "idCategory")
        })
    // @JsonManagedReference    
        private List<Category> categories;

    public Product(Long id, String name, String brand, int stock, float unitPrice,
            LocalDate releaseDate) {
        this.idProduct = id;
        this.name = name;
        this.brand = brand;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.releaseDate = releaseDate;
        this.registerDate = LocalDateTime.now();
        this.lastModDate = LocalDateTime.now();
        this.categories = new ArrayList<>();
    }

    public Product(Long id, String name, String brand, int stock, float unitPrice,
            LocalDate releaseDate, List<Category> categories) {
        this.idProduct = id;
        this.name = name;
        this.brand = brand;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.releaseDate = releaseDate;
        this.registerDate = LocalDateTime.now();
        this.lastModDate = LocalDateTime.now();
        this.categories = categories;
    }

    public Product(String name, String brand, int stock, float unitPrice,
            LocalDate releaseDate) {
        this.name = name;
        this.brand = brand;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.releaseDate = releaseDate;
        this.registerDate = LocalDateTime.now();
        this.lastModDate = LocalDateTime.now();
        this.categories = new ArrayList<>();
    }

    public Product(String name, String brand, int stock, float unitPrice,
            LocalDate releaseDate, List<Category> categories) {
        this.name = name;
        this.brand = brand;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.releaseDate = releaseDate;
        this.registerDate = LocalDateTime.now();
        this.lastModDate = LocalDateTime.now();
        this.categories = categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void remCategory(Long id){
         Optional<Category> find = this.categories.stream()
                .filter(c -> c.getIdCategory().compareTo(id) == 0)
                .findFirst();
        if (find.isPresent()) {
            this.categories.remove(find.orElseThrow());
        }
    }

    @Override
    public String toString() {
        return  "\n----p----p----p----p----p" +
                "\nID: " + this.idProduct +
                "\nName: " + this.name +
                "\nBrand: " + this.brand +
                "\nStock: " + this.stock +
                "\nUnit Price: " + this.unitPrice +
                "\nRelease Date: " + this.releaseDate +
                "\nRegister Date: " + this.registerDate +
                "\nLast Modification Date: " + this.lastModDate +
                "\n" + this.categories.stream()
                .map(Category::toStringWithoutProducts).collect(Collectors.joining("\n")) +
                "\n----p----p----p----p----p";
    }

    public String toStringWithoutCategories() {
        return  "\n----p----p----p----p----p" +
                "\nID: " + this.idProduct +
                "\nName: " + this.name +
                "\nBrand: " + this.brand +
                "\nStock: " + this.stock +
                "\nUnit Price: " + this.unitPrice +
                "\nRelease Date: " + this.releaseDate +
                "\nRegister Date: " + this.registerDate +
                "\nLast Modification Date: " + this.lastModDate +
                "\n----p----p----p----p----p";
    }
}