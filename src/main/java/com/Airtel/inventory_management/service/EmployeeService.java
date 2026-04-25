package com.Airtel.inventory_management.service;

import com.Airtel.inventory_management.model.Employee;
import com.Airtel.inventory_management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Create or update employee
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    // Get employee by code
    public Employee getEmployeeByCode(String employeeCode) {
        return employeeRepository.findByEmployeeCode(employeeCode);
    }

    // Delete employee
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    // Check if employee code exists
    public boolean isEmployeeCodeExists(String employeeCode) {
        return employeeRepository.existsByEmployeeCode(employeeCode);
    }

    // Get employees by department
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    // Get active employees
    public List<Employee> getActiveEmployees() {
        return employeeRepository.findByIsActiveTrue();
    }

    // Search employees
    public List<Employee> searchEmployees(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllEmployees();
        }
        return employeeRepository.searchEmployees(keyword);
    }

    // Deactivate employee
    public Employee deactivateEmployee(Long id) {
        Optional<Employee> empOpt = employeeRepository.findById(id);
        if (empOpt.isPresent()) {
            Employee employee = empOpt.get();
            employee.setIsActive(false);
            return employeeRepository.save(employee);
        }
        return null;
    }

    // Activate employee
    public Employee activateEmployee(Long id) {
        Optional<Employee> empOpt = employeeRepository.findById(id);
        if (empOpt.isPresent()) {
            Employee employee = empOpt.get();
            employee.setIsActive(true);
            return employeeRepository.save(employee);
        }
        return null;
    }

    // Get count by department
    public long getCountByDepartment(String department) {
        return employeeRepository.countByDepartment(department);
    }

    // Get total active employees
    public long getActiveEmployeeCount() {
        return employeeRepository.findByIsActiveTrue().size();
    }
}