package com.cybrixsystems.apipm.Model;

import java.util.List;

import javax.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "category")
public class Category{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idCategory;
    private String name;
    
    @ManyToMany(mappedBy = "categories")
    private List<Product> products;

    public Category(Long id,String name){
        this.idCategory = id;
        this.name = name;
    }
}