package com.Airtel.inventory_management.controller;

import com.Airtel.inventory_management.model.AuditLog;
import com.Airtel.inventory_management.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping
    public String viewAuditLogs(Model model) {
        List<AuditLog> logs = auditService.getRecentLogs();
        model.addAttribute("logs", logs);
        model.addAttribute("pageTitle", "Audit Trail");
        return "audit-trail";
    }

    @GetMapping("/search")
    public String searchLogs(@RequestParam(required = false) String keyword, Model model) {
        List<AuditLog> logs = auditService.searchLogs(keyword);
        model.addAttribute("logs", logs);
        model.addAttribute("searchKeyword", keyword);
        model.addAttribute("pageTitle", "Audit Trail Search");
        return "audit-trail";
    }
}