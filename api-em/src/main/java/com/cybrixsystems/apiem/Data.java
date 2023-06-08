package com.cybrixsystems.apiem;

import java.util.ArrayList;
import java.util.List;

import com.cybrixsystems.apiem.Model.Employee;

public class Data {
    public final static Employee employees1 = new Employee(1L,"Ava","O'Sullivan",37,"femenino","4543534534","dldlasdl@gmail.com", 223456575,LocalDate.of(2008, 8, 20),LocalDate.of(2008, 8, 20));
    public final static List<Employee> employees1 = new ArrayList<>(Arrays.asList(
        new Employee(1L,"Ava","O'Sullivan",37,"femenino","4543534534","dldlasdl@gmail.com", 223456575),LocalDate.of(2008, 8, 20),LocalDate.of(2008, 8, 20),
        new Employee(2L,"Sophie	","Tracy",38,"fe7menino","4543534534","dldlasdl@gmail.com", 223456575),LocalDate.of(2008, 8, 20),LocalDate.of(2008, 8, 20),
        new Employee(3L,"Tracy","Brown",39,"fem8enino","4543534534","dldlasdl@gmail.com", 223456575),LocalDate.of(2008, 8, 20),LocalDate.of(2008, 8, 20)
    ));
}
