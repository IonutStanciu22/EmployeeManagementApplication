package edu.scoalainformala.StanciuIonut.service;


import edu.scoalainformala.StanciuIonut.model.Employee;
import edu.scoalainformala.StanciuIonut.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Retrieve all employees from the database
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    // Find an employee by ID
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    // Save or update an employee
    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Delete an employee
    @Transactional
    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
}
