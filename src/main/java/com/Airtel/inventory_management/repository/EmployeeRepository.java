package com.Airtel.inventory_management.repository;

import com.Airtel.inventory_management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // Find by department
    List<Employee> findByDepartment(String department);
    
    // Find active employees
    List<Employee> findByIsActiveTrue();
    
    // Find by employee code
    Employee findByEmployeeCode(String employeeCode);
    
    // Check if employee code exists
    boolean existsByEmployeeCode(String employeeCode);
    
    // Search by name or code
    @Query("SELECT e FROM Employee e WHERE " +
           "LOWER(e.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.employeeCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.department) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Employee> searchEmployees(@Param("keyword") String keyword);
    
    // Count by department
    long countByDepartment(String department);
}