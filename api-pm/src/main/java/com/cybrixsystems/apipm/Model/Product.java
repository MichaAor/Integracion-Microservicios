package com.cybrixsystems.apipm.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
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

    @ManyToMany
    @JoinTable(name = "productCategory", joinColumns = @JoinColumn(name = "idProduct"), inverseJoinColumns = @JoinColumn(name = "idCategory"))
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

    @Override
    public String toString() {
        return "\nID: " + this.idProduct +
                "\nName: " + this.name +
                "\nBrand: " + this.brand +
                "\nStock: " + this.stock +
                "\nUnit Price: " + this.unitPrice +
                "\nRelease Date: " + this.releaseDate +
                "\nRegister Date: " + this.registerDate +
                "\nLast Modification Date: " + this.lastModDate;
    }
}