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



    @GetMapping("/search")
    public String search(@RequestParam String searchBy, @RequestParam String searchTerm, Model model) {
        List<Employee> employees = new ArrayList<>();

        // Verificare pentru condiția specifică "email" ce trebuie să conțină "@"
        if ("email".equals(searchBy) && !searchTerm.contains("@")) {
            model.addAttribute("noResults", "Search term for department must contain '@'.");
            return "index";
        }

        // Verificare pentru condiția ca "firstName", "lastName", și "department" să nu conțină cifre
        if (("firstName".equals(searchBy) || "lastName".equals(searchBy) || "department".equals(searchBy)) && !searchTerm.matches("[a-zA-Z ]+")) {
            model.addAttribute("noResults", "Search term for " + searchBy + " must contain only letters.");
            return "index";
        }

        switch (searchBy) {
            case "firstName":
                employees = employeeService.searchByFirstName(searchTerm);
                break;
            case "lastName":
                employees = employeeService.searchByLastName(searchTerm);
                break;
            case "email":
                employees = employeeService.searchByEmail(searchTerm);
                break;
            case "department":
                employees = employeeService.searchByDepartment(searchTerm);
                break;
        }

        if (employees.isEmpty()) {
            model.addAttribute("noResults", "No results found for: " + searchTerm);
        } else {
            model.addAttribute("employees", employees);
        }
        return "index"; // Înapoi la pagina principală cu rezultatele căutării sau mesajul de eroare
    }

    @GetMapping("/employees/searchById")
    public String searchEmployeeById(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            model.addAttribute("employee", employee);
            return "details-employee";  // Redirecționează către pagina de detalii a angajatului
        } else {
            redirectAttributes.addFlashAttribute("error", "No employee found with ID " + id);
            return "redirect:/";  // Redirecționează înapoi la pagina principală dacă ID-ul nu este găsit
        }
    }
    @GetMapping("/employees/details/{id}")
    public String showEmployeeDetails(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + id));
        model.addAttribute("employee", employee);
        return "details-employee"; // Numele template-ului Thymeleaf pentru detalii
    }



}

