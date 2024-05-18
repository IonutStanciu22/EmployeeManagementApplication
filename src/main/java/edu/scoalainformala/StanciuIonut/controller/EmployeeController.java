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
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String homepage() {
        return "redirect:/";
    }

    @GetMapping
    public String showEmployeeList(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit-employee";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employeeDetails) {
        employeeService.updateEmployee(id, employeeDetails);
        return "redirect:/employees";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }

    @GetMapping("/search")
    public String search(@RequestParam String searchBy, @RequestParam String searchTerm, Model model) {
        List<Employee> employees = employeeService.searchEmployees(searchBy, searchTerm);
        if (employees.isEmpty()) {
            model.addAttribute("noResults", "No results found for: " + searchTerm);
        } else {
            model.addAttribute("employees", employees);
        }
        return "employees-list";
    }

    @GetMapping("/searchById")
    public String searchEmployeeById(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Employee employee = employeeService.findEmployeeById(id);
            model.addAttribute("employee", employee);
            return "details-employee";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/employees";
        }
    }

    @GetMapping("/details/{id}")
    public String showEmployeeDetails(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        if (employee == null) {
            model.addAttribute("errorMessage", "Employee not found");
            return "error";
        }
        model.addAttribute("employee", employee);
        return "details-employee";
    }
}
