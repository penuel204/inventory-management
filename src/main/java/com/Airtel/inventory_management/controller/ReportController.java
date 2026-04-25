package com.Airtel.inventory_management.controller;

import com.Airtel.inventory_management.model.Assignment;
import com.Airtel.inventory_management.model.Device;
import com.Airtel.inventory_management.model.Issue;
import com.Airtel.inventory_management.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public String reportsMenu(Model model) {
        model.addAttribute("pageTitle", "Reports");
        return "reports";
    }

    @GetMapping("/devices-by-status")
    public String devicesByStatus(Model model) {
        Map<String, List<Device>> report = reportService.getDevicesByStatusReport();
        model.addAttribute("report", report);
        model.addAttribute("reportTitle", "Devices by Status");
        model.addAttribute("pageTitle", "Devices by Status Report");
        return "device-report";
    }

    @GetMapping("/devices-by-type")
    public String devicesByType(Model model) {
        Map<String, List<Device>> report = reportService.getDevicesByTypeReport();
        model.addAttribute("report", report);
        model.addAttribute("reportTitle", "Devices by Type");
        model.addAttribute("pageTitle", "Devices by Type Report");
        return "device-report";
    }

    @GetMapping("/active-assignments")
    public String activeAssignments(Model model) {
        List<Assignment> assignments = reportService.getActiveAssignmentsReport();
        model.addAttribute("assignments", assignments);
        model.addAttribute("reportTitle", "Active Assignments");
        model.addAttribute("pageTitle", "Active Assignments Report");
        return "active-assignments";
    }

    @GetMapping("/open-issues")
    public String openIssues(Model model) {
        List<Issue> issues = reportService.getOpenIssuesReport();
        model.addAttribute("issues", issues);
        model.addAttribute("reportTitle", "Open Issues");
        model.addAttribute("pageTitle", "Open Issues Report");
        return "open-issues";
    }

    @GetMapping("/department-distribution")
    public String departmentDistribution(Model model) {
        Map<String, Long> distribution = reportService.getDepartmentWiseDistribution();
        model.addAttribute("distribution", distribution);
        model.addAttribute("reportTitle", "Department-wise Distribution");
        model.addAttribute("pageTitle", "Department Distribution Report");
        return "dept-distribution";
    }
}