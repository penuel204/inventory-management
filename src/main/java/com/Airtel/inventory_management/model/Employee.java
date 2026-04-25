package com.Airtel.inventory_management.model;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String employeeCode;
    
    private String fullName;
    private String department;
    private String role;
    private String email;
    private String phoneNumber;
    private Boolean isActive;

    public Employee() {
        this.isActive = true;
    }

    // Getters
    public Long getId() { return id; }
    public String getEmployeeCode() { return employeeCode; }
    public String getFullName() { return fullName; }
    public String getDepartment() { return department; }
    public String getRole() { return role; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public Boolean getIsActive() { return isActive; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setDepartment(String department) { this.department = department; }
    public void setRole(String role) { this.role = role; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}