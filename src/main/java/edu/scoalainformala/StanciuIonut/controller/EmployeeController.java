package edu.scoalainformala.StanciuIonut.controller;

import edu.scoalainformala.StanciuIonut.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.scoalainformala.StanciuIonut.model.Employee;
import edu.scoalainformala.StanciuIonut.repository.EmployeeRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    // Afisarea listei angajatilor
    @GetMapping("/")
    public String showEmployeeList(Model model) {
        model.addAttribute("employees", employeeService.findAll());
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
        employeeService.saveEmployee(employee);
        return "redirect:/";  // Redirectioneaza la pagina principala dupa adaugare
    }

    // Afisarea formularului de editare angajat
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit-employee"; // Utilizează edit-employee.html pentru editare
    }

    // Actualizarea unui angajat
    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employeeDetails) {
        employeeService.updateEmployee(id, employeeDetails);
        return "redirect:/";  // Redirecționează la pagina principală după actualizare
    }


    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/";  // Redirecționează la pagina principală după ștergere
    }




    @GetMapping("/search")
    public String search(@RequestParam String searchBy, @RequestParam String searchTerm, Model model) {
        List<Employee> employees = employeeService.searchEmployees(searchBy, searchTerm);

        if (employees.isEmpty()) {
            model.addAttribute("noResults", "No results found for: " + searchTerm);
        } else {
            model.addAttribute("employees", employees);
        }
        return "index"; // Înapoi la pagina principală cu rezultatele căutării sau mesajul de eroare
    }
    @GetMapping("/employees/searchById")
    public String searchEmployeeById(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Employee employee = employeeService.findEmployeeById(id);
            model.addAttribute("employee", employee);
            return "details-employee";  // Redirecționează către pagina de detalii a angajatului
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";  // Redirecționează înapoi la pagina principală dacă ID-ul nu este găsit
        }
    }

    @GetMapping("/employees/details/{id}")
    public String showEmployeeDetails(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        model.addAttribute("employee", employee);
        return "details-employee"; // Numele template-ului Thymeleaf pentru detalii
    }
}

