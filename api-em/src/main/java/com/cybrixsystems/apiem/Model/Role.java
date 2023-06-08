package com.cybrixsystems.apiem.Model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idRole;
    private String roleName;
    @OneToMany(mappedBy = "role")
    private List<Employee> employees;

}
