package com.cybrixsystems.apipm.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategory;
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;

    public Category(Long id, String name) {
        this.idCategory = id;
        this.name = name;
        this.products = new ArrayList<>();
    }

    public Category(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public Category(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void remProduct(Long id) {
        Optional<Product> find = this.products.stream()
                .filter(p -> p.getIdProduct().compareTo(id) == 0)
                .findFirst();
        if (find.isPresent()) {
            this.products.remove(find.orElseThrow());
        }
    }

    @Override
    public String toString() {
        return "\n----c----c----c----c----c" +
                "\nID: " + this.idCategory +
                "\nName: " + this.name +
                "\n" + this.products.stream()
                .map(Product::toStringWithoutCategories).collect(Collectors.joining("\n")) +
                "\n----c----c----c----c----c";
    }

    public String toStringWithoutProducts() {
        return "\n----c----c----c----c----c" +
                "\nID: " + this.idCategory +
                "\nName: " + this.name +
                "\n----c----c----c----c----c";
    }
}