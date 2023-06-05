package com.cybrixsystems.apipm.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class Product{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idProduct;
    private String name;
    private String brand;
    private int stock;
    private float unitPrice;
    private LocalDate releaseDate;
    private LocalDateTime creationDate;
    private LocalDateTime lastModDate;

    @ManyToMany
    @JoinTable(
        name ="productCategory",
        joinColumns = @JoinColumn(name = "idProduct"),
        inverseJoinColumns = @JoinColumn(name = "idCategory")
    )
    private List<Category> categories;

    public Product(Long id, String name, String brand, int stock, float unitPrice,
                    LocalDate releaseDate){
        this.idProduct = id;                
        this.name = name;                
        this.brand = brand;                
        this.stock = stock;                
        this.unitPrice = unitPrice;                
        this.releaseDate = releaseDate; 
        this.creationDate = LocalDateTime.now();
        this.lastModDate = LocalDateTime.now();               
    }

    public Product(Long id, String name, String brand, int stock, float unitPrice,
                    LocalDate releaseDate,List<Category> categories){
        this.idProduct = id;                
        this.name = name;                
        this.brand = brand;                
        this.stock = stock;                
        this.unitPrice = unitPrice;                
        this.releaseDate = releaseDate; 
        this.creationDate = LocalDateTime.now();
        this.lastModDate = LocalDateTime.now();
        this.categories = categories;               
    }
}