package com.cybrixsystems.apiem.Model;
import javax.persistence.*;

@Entity
@Table(name = "adrees")
public class Adress {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idAdress;
    private String street;
    private int number;
    
}
