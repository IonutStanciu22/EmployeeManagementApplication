package edu.scoalainformala.StanciuIonut.controller;

import edu.scoalainformala.StanciuIonut.model.Salary;
import edu.scoalainformala.StanciuIonut.service.EmployeeService;
import edu.scoalainformala.StanciuIonut.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/employees/salaries")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;
   @Autowired
    private EmployeeService employeeService;

    // Listarea tuturor salariilor
    @GetMapping
    public String listSalaries(Model model) {
        model.addAttribute("salaries", salaryService.findAll());
        return "salaries-list"; // Un template HTML pentru a arăta lista de salarii
    }

    // Adăugarea unui nou salariu (formular)
    @GetMapping("/add")
    public String showAddSalaryForm(Model model) {
        model.addAttribute("salary", new Salary());
        model.addAttribute("employees", employeeService.findAll()); // Presupunând că ai o metodă findAll() care returnează toți angajații
        return "add-salary"; // Formular pentru adăugarea unui nou salariu
    }

    // Procesarea adăugării unui salariu
    @PostMapping("/add")
    public String addSalary(@ModelAttribute Salary salary) {
        salaryService.saveSalary(salary);
        return "redirect:/employees/salaries";
    }

    // Formularul de editare a salariului
    @GetMapping("/edit/{id}")
    public String showEditSalaryForm(@PathVariable Long id, Model model) {
        Salary salary = salaryService.findById(id);
        model.addAttribute("salary", salary);
        return "edit-salary"; // Formular pentru editarea unui salariu
    }

    // Actualizarea salariului
    @PostMapping("/update/{id}")
    public String updateSalary(@PathVariable Long id, @ModelAttribute Salary salaryDetails) {
        salaryService.updateSalary(id, salaryDetails);
        return "redirect:/employees/salaries";
    }

    // Ștergerea unui salariu
    @PostMapping("/delete/{id}")
    public String deleteSalary(@PathVariable Long id) {
        salaryService.deleteSalary(id);
        return "redirect:/employees/salaries";
    }
}
