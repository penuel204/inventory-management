package com.Airtel.inventory_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    // Hardcoded users
    private static final Map<String, String> USERS = new HashMap<>();
    
    static {
        USERS.put("24rp05465", "24rp03949");
        USERS.put("24rp03949", "24rp05465");
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        
        // Check if username exists and password matches
        if (USERS.containsKey(username) && USERS.get(username).equals(password)) {
            session.setAttribute("loggedInUser", username);
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password!");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}