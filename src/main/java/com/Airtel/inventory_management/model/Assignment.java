package com.Airtel.inventory_management.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    private LocalDateTime assignedDate;
    private LocalDateTime returnedDate;
    private String conditionAtAssignment;
    private String notes;

    public Assignment() {
        this.assignedDate = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public Device getDevice() { return device; }
    public Employee getEmployee() { return employee; }
    public LocalDateTime getAssignedDate() { return assignedDate; }
    public LocalDateTime getReturnedDate() { return returnedDate; }
    public String getConditionAtAssignment() { return conditionAtAssignment; }
    public String getNotes() { return notes; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setDevice(Device device) { this.device = device; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public void setAssignedDate(LocalDateTime assignedDate) { this.assignedDate = assignedDate; }
    public void setReturnedDate(LocalDateTime returnedDate) { this.returnedDate = returnedDate; }
    public void setConditionAtAssignment(String conditionAtAssignment) { this.conditionAtAssignment = conditionAtAssignment; }
    public void setNotes(String notes) { this.notes = notes; }
}