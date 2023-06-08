package com.cybrixsystems.apiem.Model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idB;
    private String nameBranch;
    @OneToMany(mappedBy = "branch")
    private List<Employee> employees;
}
