package com.cybrixsystems.apiem.Service;
import java.util.List;
import java.util.Optional;

import com.cybrixsystems.apiem.Model.Employee;

public interface EmployeeService{
    public List<Employee> findAll();
    public Optional<Employee> findById(Long idEm);
    public Employee findByName(String name);
    public Employee save(Employee employee);
    public void deleteById(Long idEm);
    
}