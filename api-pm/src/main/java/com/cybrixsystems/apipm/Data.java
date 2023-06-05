package com.cybrixsystems.apipm;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.cybrixsystems.apipm.Model.Category;
import com.cybrixsystems.apipm.Model.Product;

public class Data{
    public final static Category CATEGORY = new Category(1L,"Manga");


    public final static List<Category> CATEGORIES = Arrays.asList(
        new Category(1L,"Manga"),
        new Category(2L,"Comic"),
        new Category(3L,"Book")
    );

    public static List<Category> EDITABLE_CATEGORIES = Arrays.asList(
        new Category(1L,"Manga"),
        new Category(2L,"Comic"),
        new Category(3L,"Book")
    );

    public final static List<Product> PRODUCTS = Arrays.asList(
        new Product(1L,"Saint Seiya Episode G 12","Ivrea",15
        ,2600f,LocalDate.of(2008, 8, 20)),
        new Product(2L,"Spiderman - Back in black 1","Marvel"
        ,10,5700f,LocalDate.of(2007, 4, 20)),
        new Product(3L,"The Hunger Games","Penguin"
        ,8,8000f,LocalDate.of(2008, 9, 14)),
        new Product(4L,"Star Wars - The Old Republic","Dark Horse"
        ,3,4500f,LocalDate.of(2006, 2, 12)),
        new Product(5L,"Gantz 5","Ivrea"
        ,23,4500f,LocalDate.of(2002, 2, 19))
    );

    public final static List<Product> PRODUCTS_CATEGORIES = Arrays.asList(
        new Product(1L,"Saint Seiya Episode G 12","Ivrea",15
        ,2600f,LocalDate.of(2008, 8, 20)
        ,Arrays.asList(new Category(1L,"Manga"))),
        new Product(2L,"Spiderman - Back in black 1","Marvel"
        ,10,5700f,LocalDate.of(2007, 4, 20)
        ,Arrays.asList(new Category(2L,"Comic"))),
        new Product(3L,"The Hunger Games","Penguin"
        ,8,8000f,LocalDate.of(2008, 9, 14)
        ,Arrays.asList(new Category(3L,"Book"))),
        new Product(4L,"Star Wars - The Old Republic","Dark Horse"
        ,3,4500f,LocalDate.of(2006, 2, 12)
        ,Arrays.asList(new Category(2L,"Comic"))),
        new Product(5L,"Gantz 5","Ivrea"
        ,23,4500f,LocalDate.of(2002, 2, 19)
        ,Arrays.asList(new Category(1L,"Manga")))
    );
}