package com.Airtel.inventory_management.controller;

import com.Airtel.inventory_management.model.Employee;
import com.Airtel.inventory_management.service.EmployeeService;
import com.Airtel.inventory_management.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuditService auditService;

    @GetMapping
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        model.addAttribute("newEmployee", new Employee());
        model.addAttribute("pageTitle", "Employee Management");
        return "employee-list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("pageTitle", "Add New Employee");
        return "employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        try {
            employeeService.saveEmployee(employee);
            redirectAttributes.addFlashAttribute("success", "Employee added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Employee employee = employeeService.getEmployeeById(id).orElse(null);
            if (employee == null) {
                redirectAttributes.addFlashAttribute("error", "Employee not found!");
                return "redirect:/employees";
            }
            model.addAttribute("employee", employee);
            model.addAttribute("pageTitle", "Edit Employee");
            return "employee-form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error loading employee: " + e.getMessage());
            return "redirect:/employees";
        }
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        try {
            employee.setId(id);
            employeeService.saveEmployee(employee);
            redirectAttributes.addFlashAttribute("success", "Employee updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating employee: " + e.getMessage());
        }
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteEmployee(id);
            redirectAttributes.addFlashAttribute("success", "Employee deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting employee: " + e.getMessage());
        }
        return "redirect:/employees";
    }

    @GetMapping("/search")
    public String searchEmployees(@RequestParam(required = false) String keyword, Model model) {
        List<Employee> employees = employeeService.searchEmployees(keyword);
        model.addAttribute("employees", employees);
        model.addAttribute("newEmployee", new Employee());
        model.addAttribute("searchKeyword", keyword);
        model.addAttribute("pageTitle", "Search Results");
        return "employee-list";
    }

    @GetMapping("/toggle/{id}")
    public String toggleStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Employee employee = employeeService.getEmployeeById(id).orElse(null);
            if (employee != null) {
                if (employee.getIsActive()) {
                    employeeService.deactivateEmployee(id);
                    redirectAttributes.addFlashAttribute("success", "Employee deactivated!");
                } else {
                    employeeService.activateEmployee(id);
                    redirectAttributes.addFlashAttribute("success", "Employee activated!");
                }
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error toggling status: " + e.getMessage());
        }
        return "redirect:/employees";
    }
}