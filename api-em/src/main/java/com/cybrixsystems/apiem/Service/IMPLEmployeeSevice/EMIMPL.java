package com.cybrixsystems.apiem.Service.IMPLEmployeeSevice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybrixsystems.apiem.Model.Employee;
import com.cybrixsystems.apiem.Repository.EmployeeRepository;
import com.cybrixsystems.apiem.Service.EmployeeService;

@Service
public class EMIMPL implements EmployeeService {
    @Autowired
    private EmployeeRepository er;
    @Override
    public void deleteById(Long idEm) {
        this.er.deleteById(idEm);
        
    }

    @Override
    public List<Employee> findAll() {
        return (List<Employee>) this.er.findAll();
    }

    @Override
    public Optional<Employee> findById(Long idEm) {
        return this.er.findById(idEm);
    }

    @Override
    public Employee findByName(String nameE) {
        return this.findByName(nameE);
    }

    @Override
    public Employee save(Employee employee) {
        employee.setName(employee.getName());
        return this.er.save(employee);
    }
    
}
