package com.cybrixsystems.apiem.Model;

import javax.persistence.*;

@Entity
@Table(name = "homeAdress")
public class HomeAdress extends Adress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String floorNum;
    private String departament;
}
