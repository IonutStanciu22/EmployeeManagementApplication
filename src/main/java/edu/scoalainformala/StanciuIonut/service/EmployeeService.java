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

    public List<Employee> searchByFirstName(String firstName) {
        return employeeRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    public List<Employee> searchByLastName(String lastName) {
        return employeeRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    public List<Employee> searchByEmail(String email) {
        return employeeRepository.findByEmailContainingIgnoreCase(email);
    }

    public List<Employee> searchByDepartment(String department) {
        return employeeRepository.findByDepartmentContainingIgnoreCase(department);
    }
}
