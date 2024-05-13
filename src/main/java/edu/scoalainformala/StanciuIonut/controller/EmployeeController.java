package edu.scoalainformala.StanciuIonut.controller;

import edu.scoalainformala.StanciuIonut.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.scoalainformala.StanciuIonut.model.Employee;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/")
    public String homepage() {

        return "index"; // Utilizeaza index.html pentru a afisa lista
    }

    // Afisarea listei angajatilor
    @GetMapping("/employees")
    public String showEmployeeList(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees-list"; // Utilizeaza employees-list.html pentru a afisa lista
    }

    // Afisarea formularului de adaugare angajat
    @GetMapping("/employees/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee"; // Utilizeaza add-employee.html pentru formular
    }

    // Procesarea formularului de adaugare angajat
    @PostMapping("/employees/add")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employees";  // Redirectioneaza la pagina principala dupa adaugare
    }

    // Afisarea formularului de editare angajat
    @GetMapping("/employees/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit-employee"; // Utilizează edit-employee.html pentru editare
    }

    // Actualizarea unui angajat
    @PostMapping("/employees/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employeeDetails) {
        employeeService.updateEmployee(id, employeeDetails);
        return "redirect:/employees";  // Redirecționează la pagina principală după actualizare
    }


    @PostMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";  // Redirecționează la pagina principală după ștergere
    }




    @GetMapping("/employees/search")
    public String search(@RequestParam String searchBy, @RequestParam String searchTerm, Model model) {
        List<Employee> employees = employeeService.searchEmployees(searchBy, searchTerm);

        if (employees.isEmpty()) {
            model.addAttribute("noResults", "No results found for: " + searchTerm);
        } else {
            model.addAttribute("employees", employees);
        }
        return "employees-list"; // Înapoi la pagina principală cu rezultatele căutării sau mesajul de eroare
    }
    @GetMapping("/employees/searchById")
    public String searchEmployeeById(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Employee employee = employeeService.findEmployeeById(id);
            model.addAttribute("employee", employee);
            return "details-employee";  // Redirecționează către pagina de detalii a angajatului
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/employees";  // Redirecționează înapoi la pagina principală dacă ID-ul nu este găsit
        }
    }

    @GetMapping("/employees/details/{id}")
    public String showEmployeeDetails(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        if (employee == null) {
            model.addAttribute("errorMessage", "Employee not found");
            return "error"; // Numele template-ului Thymeleaf pentru erori
        }
        model.addAttribute("employee", employee);
        return "details-employee"; // Numele template-ului Thymeleaf pentru detalii
    }
}