package edu.scoalainformala.StanciuIonut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.scoalainformala.StanciuIonut.model.Employee;
import edu.scoalainformala.StanciuIonut.repository.EmployeeRepository;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Afisarea listei angajatilor
    @GetMapping("/")
    public String showEmployeeList(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "index"; // Utilizeaza index.html pentru a afisa lista
    }

    // Afisarea formularului de adaugare angajat
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee"; // Utilizeaza add-employee.html pentru formular
    }

    // Procesarea formularului de adaugare angajat
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/";  // Redirectioneaza la pagina principala dupa adaugare
    }

    // Afisarea formularului de editare angajat
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        return "edit-employee"; // Utilizeaza edit-employee.html pentru editare
    }

    // Actualizarea unui angajat
    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employeeDetails, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(employeeDetails.getDepartment());
        employeeRepository.save(employee);
        return "redirect:/"; // Redirectioneaza la pagina principala dupa actualizare
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employeeRepository.delete(employee);
        return "redirect:/"; // Sau redirecționează unde consideri necesar
    }

    }

