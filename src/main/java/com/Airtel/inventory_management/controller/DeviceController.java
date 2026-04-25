package com.Airtel.inventory_management.controller;

import com.Airtel.inventory_management.model.Device;
import com.Airtel.inventory_management.service.DeviceService;
import com.Airtel.inventory_management.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private AuditService auditService;

    @GetMapping
    public String listDevices(Model model) {
        List<Device> devices = deviceService.getAllDevices();
        model.addAttribute("devices", devices);
        model.addAttribute("newDevice", new Device());
        model.addAttribute("pageTitle", "Device Management");
        return "device-list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("device", new Device());
        model.addAttribute("pageTitle", "Add New Device");
        return "device-form";
    }

    @PostMapping("/save")
    public String saveDevice(@ModelAttribute Device device, RedirectAttributes redirectAttributes) {
        try {
            if (device.getDeviceCondition() == null || device.getDeviceCondition().isEmpty()) {
                device.setDeviceCondition("Good");
            }
            if (device.getStatus() == null || device.getStatus().isEmpty()) {
                device.setStatus("Available");
            }
            
            deviceService.saveDevice(device);
            redirectAttributes.addFlashAttribute("success", "Device added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/devices";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Device device = deviceService.getDeviceById(id).orElse(null);
            if (device == null) {
                redirectAttributes.addFlashAttribute("error", "Device not found!");
                return "redirect:/devices";
            }
            model.addAttribute("device", device);
            model.addAttribute("pageTitle", "Edit Device");
            return "device-form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error loading device: " + e.getMessage());
            return "redirect:/devices";
        }
    }

    @PostMapping("/update/{id}")
    public String updateDevice(@PathVariable Long id, @ModelAttribute Device device, RedirectAttributes redirectAttributes) {
        try {
            device.setId(id);
            deviceService.saveDevice(device);
            redirectAttributes.addFlashAttribute("success", "Device updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating device: " + e.getMessage());
        }
        return "redirect:/devices";
    }

    @GetMapping("/delete/{id}")
    public String deleteDevice(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            deviceService.deleteDevice(id);
            redirectAttributes.addFlashAttribute("success", "Device deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting device: " + e.getMessage());
        }
        return "redirect:/devices";
    }

    @GetMapping("/view/{id}")
    public String viewDevice(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Device device = deviceService.getDeviceById(id).orElse(null);
            if (device == null) {
                redirectAttributes.addFlashAttribute("error", "Device not found!");
                return "redirect:/devices";
            }
            model.addAttribute("device", device);
            model.addAttribute("pageTitle", "Device Details");
            return "device-detail";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error loading device: " + e.getMessage());
            return "redirect:/devices";
        }
    }

    @GetMapping("/search")
    public String searchDevices(@RequestParam(required = false) String keyword, Model model) {
        List<Device> devices = deviceService.searchDevices(keyword);
        model.addAttribute("devices", devices);
        model.addAttribute("newDevice", new Device());
        model.addAttribute("searchKeyword", keyword);
        model.addAttribute("pageTitle", "Search Results");
        return "device-list";
    }
}