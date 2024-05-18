package edu.scoalainformala.StanciuIonut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.scoalainformala.StanciuIonut.model.Salary;
import edu.scoalainformala.StanciuIonut.repository.SalaryRepository;

import java.util.List;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    public List<Salary> findAll() {
        return salaryRepository.findAll();
    }

    public Salary findById(Long id) {
        return salaryRepository.findById(id).orElse(null);
    }

    public void saveSalary(Salary salary) {
        salaryRepository.save(salary);
    }

    public void updateSalary(Long id, Salary salaryDetails) {
        Salary salary = findById(id);
        if (salary != null) {
            salary.setAmount(salaryDetails.getAmount());
            salary.setEffectiveDate(salaryDetails.getEffectiveDate());
            salaryRepository.save(salary);
        }
    }

    public void deleteSalary(Long id) {
        salaryRepository.deleteById(id);
    }
}
