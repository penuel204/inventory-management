package com.Airtel.inventory_management.controller;

import com.Airtel.inventory_management.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/")
    public String dashboard(Model model, HttpSession session) {
        // Check if user is logged in
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        
        Map<String, Object> summary = reportService.getDashboardSummary();
        
        model.addAttribute("totalDevices", summary.get("totalDevices"));
        model.addAttribute("availableDevices", summary.get("availableDevices"));
        model.addAttribute("assignedDevices", summary.get("assignedDevices"));
        model.addAttribute("repairDevices", summary.get("repairDevices"));
        
        model.addAttribute("totalEmployees", summary.get("totalEmployees"));
        model.addAttribute("activeEmployees", summary.get("activeEmployees"));
        
        model.addAttribute("activeAssignments", summary.get("activeAssignments"));
        model.addAttribute("openIssues", summary.get("openIssues"));
        
        model.addAttribute("laptops", summary.get("laptops"));
        model.addAttribute("desktops", summary.get("desktops"));
        model.addAttribute("mobiles", summary.get("mobiles"));
        
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("username", session.getAttribute("loggedInUser"));
        return "index";
    }
}