package edu.scoalainformala.StanciuIonut.repository;

import edu.scoalainformala.StanciuIonut.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
}