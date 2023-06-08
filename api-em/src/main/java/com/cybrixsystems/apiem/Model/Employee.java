package com.cybrixsystems.apiem.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEm;
    private String name;
    private String lasname;
    private int age;
    private String gender;
    private String dni;
    private String email;
    private int phone;
    private LocalDate birthdate;
    private LocalDate regDate;
    @ManyToOne
    @JoinColumn(name = "idRole")
    private Role role;
    @ManyToOne
    @JoinColumn(name = "idB")
    private Branch branch;

    

}
