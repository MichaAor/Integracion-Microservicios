package com.cybrixsystems.apipm.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
       this.products = new ArrayList<>();
   }

   public Category(String name){
       this.name = name;
       this.products = new ArrayList<>();
   }

   public void addProduct(Product product){
       this.products.add(product);
   }

   public void remProduct(Long id){
    Optional<Product> find = this.products.stream()
                                .filter(p -> p.getIdProduct().compareTo(id) == 0)
                                .findFirst();
    if(find.isPresent()){
        this.products.remove(find.orElseThrow());
    }
   }
}