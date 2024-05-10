package edu.scoalainformala.StanciuIonut.service;

import edu.scoalainformala.StanciuIonut.model.Employee;
import edu.scoalainformala.StanciuIonut.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
    public void updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setDateOfBirth(employeeDetails.getDateOfBirth());
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employeeRepository.delete(employee);
    }
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id: " + id));

    }
    public List<Employee> searchEmployees(String searchBy, String searchTerm) {
        if ("email".equals(searchBy) && searchTerm.contains("@")) {
            return employeeRepository.findByEmailContainingIgnoreCase(searchTerm);
        }
        if ("firstName".equals(searchBy) && searchTerm.matches("[a-zA-Z ]+")) {
            return employeeRepository.findByFirstNameContainingIgnoreCase(searchTerm);
        }
        if ("lastName".equals(searchBy) && searchTerm.matches("[a-zA-Z ]+")) {
            return employeeRepository.findByLastNameContainingIgnoreCase(searchTerm);
        }
        if ("department".equals(searchBy) && searchTerm.matches("[a-zA-Z ]+")) {
            return employeeRepository.findByDepartmentContainingIgnoreCase(searchTerm);
        }
        return new ArrayList<>();
    }

    }

