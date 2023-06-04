package com.cybrixsystems.apipm.Model;

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
    private LocalDateTime releaseDate;
    private LocalDateTime creationDate;
    private LocalDateTime lastModDate;

    @ManyToMany
    @JoinTable(
        name ="productCategory",
        joinColumns = @JoinColumn(name = "idProduct"),
        inverseJoinColumns = @JoinColumn(name = "idCategory")
    )
    private List<Category> categories;
}