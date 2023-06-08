package com.cybrixsystems.apiem.Repository;

import org.springframework.data.repository.CrudRepository;

import com.cybrixsystems.apiem.Model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    
}
