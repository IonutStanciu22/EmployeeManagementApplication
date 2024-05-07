package edu.scoalainformala.StanciuIonut.repository;


import edu.scoalainformala.StanciuIonut.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Aici poți adăuga metode de interogare personalizate, dacă este necesar
    List<Employee> findByFirstNameContainingIgnoreCase(String firstName);
    List<Employee> findByLastNameContainingIgnoreCase(String lastName);
    List<Employee> findByEmailContainingIgnoreCase(String email);
    List<Employee> findByDepartmentContainingIgnoreCase(String department);
}
