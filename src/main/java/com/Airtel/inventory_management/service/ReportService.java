package com.Airtel.inventory_management.service;

import com.Airtel.inventory_management.model.*;
import com.Airtel.inventory_management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private IssueRepository issueRepository;

    // Dashboard summary
    public Map<String, Object> getDashboardSummary() {
        Map<String, Object> summary = new HashMap<>();
        
        summary.put("totalDevices", deviceRepository.count());
        summary.put("availableDevices", deviceRepository.countByStatus("Available"));
        summary.put("assignedDevices", deviceRepository.countByStatus("Assigned"));
        summary.put("repairDevices", deviceRepository.countByStatus("Under Repair"));
        
        summary.put("totalEmployees", employeeRepository.count());
        summary.put("activeEmployees", employeeRepository.findByIsActiveTrue().size());
        
        summary.put("activeAssignments", assignmentRepository.countByReturnedDateIsNull());
        summary.put("openIssues", issueRepository.countByStatus("Open"));
        
        summary.put("laptops", deviceRepository.countByDeviceType("Laptop"));
        summary.put("desktops", deviceRepository.countByDeviceType("Desktop"));
        summary.put("mobiles", deviceRepository.countByDeviceType("Mobile"));
        
        return summary;
    }

    // Devices by status report
    public Map<String, List<Device>> getDevicesByStatusReport() {
        Map<String, List<Device>> report = new HashMap<>();
        report.put("Available", deviceRepository.findByStatus("Available"));
        report.put("Assigned", deviceRepository.findByStatus("Assigned"));
        report.put("Under Repair", deviceRepository.findByStatus("Under Repair"));
        return report;
    }

    // Devices by type report
    public Map<String, List<Device>> getDevicesByTypeReport() {
        Map<String, List<Device>> report = new HashMap<>();
        report.put("Laptop", deviceRepository.findByDeviceType("Laptop"));
        report.put("Desktop", deviceRepository.findByDeviceType("Desktop"));
        report.put("Mobile", deviceRepository.findByDeviceType("Mobile"));
        return report;
    }

    // Active assignments report
    public List<Assignment> getActiveAssignmentsReport() {
        return assignmentRepository.findByReturnedDateIsNull();
    }

    // Open issues report
    public List<Issue> getOpenIssuesReport() {
        return issueRepository.findByStatus("Open");
    }

    // Department wise device distribution
    public Map<String, Long> getDepartmentWiseDistribution() {
        Map<String, Long> distribution = new HashMap<>();
        List<Assignment> assignments = assignmentRepository.findByReturnedDateIsNull();
        
        for (Assignment assignment : assignments) {
            String department = assignment.getEmployee().getDepartment();
            distribution.put(department, distribution.getOrDefault(department, 0L) + 1);
        }
        
        return distribution;
    }
}