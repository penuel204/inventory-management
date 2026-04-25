package com.Airtel.inventory_management.controller;

import com.Airtel.inventory_management.model.Assignment;
import com.Airtel.inventory_management.model.Device;
import com.Airtel.inventory_management.model.Employee;
import com.Airtel.inventory_management.service.AssignmentService;
import com.Airtel.inventory_management.service.DeviceService;
import com.Airtel.inventory_management.service.EmployeeService;
import com.Airtel.inventory_management.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuditService auditService;

    @GetMapping
    public String listAssignments(Model model) {
        List<Assignment> currentAssignments = assignmentService.getCurrentAssignments();
        List<Assignment> allAssignments = assignmentService.getAllAssignments();
        List<Device> availableDevices = deviceService.getAvailableDevices();
        List<Employee> activeEmployees = employeeService.getActiveEmployees();
        
        model.addAttribute("currentAssignments", currentAssignments);
        model.addAttribute("allAssignments", allAssignments);
        model.addAttribute("availableDevices", availableDevices);
        model.addAttribute("activeEmployees", activeEmployees);
        model.addAttribute("pageTitle", "Assignment Management");
        return "assignment-list";
    }

    @PostMapping("/assign")
    public String assignDevice(@RequestParam Long deviceId, 
                               @RequestParam Long employeeId,
                               @RequestParam(required = false) String condition,
                               @RequestParam(required = false) String notes,
                               RedirectAttributes redirectAttributes) {
        try {
            Assignment assignment = assignmentService.assignDevice(deviceId, employeeId, condition, notes);
            if (assignment != null) {
                redirectAttributes.addFlashAttribute("success", "Device assigned successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Device is already assigned!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/assignments";
    }

    @GetMapping("/return/{deviceId}")
    public String returnDevice(@PathVariable Long deviceId, 
                               @RequestParam(required = false) String notes,
                               RedirectAttributes redirectAttributes) {
        try {
            Assignment assignment = assignmentService.returnDevice(deviceId, notes);
            if (assignment != null) {
                redirectAttributes.addFlashAttribute("success", "Device returned successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Device is not currently assigned!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/assignments";
    }
}