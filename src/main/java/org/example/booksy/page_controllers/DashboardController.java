package org.example.booksy.page_controllers;

import jakarta.servlet.http.HttpSession;
import org.example.booksy.model.User;
import org.example.booksy.service.ServiceItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/provider")
    public String providerDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null || user.getRole() != User.Role.PROVIDER) {
            return "redirect:/users/login";
        }

        Long profileId = (Long) session.getAttribute("profileId");
        if (profileId == null) {
            model.addAttribute("needsProfile", true);
        } else {
            model.addAttribute("profileId", profileId);
            model.addAttribute("needsProfile", false);
        }

        model.addAttribute("userId", user.getId());
        return "dashboard-provider";
    }

    @GetMapping("/customer")
    public String customerDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null || user.getRole() != User.Role.CUSTOMER) {
            return "redirect:/users/login";
        }

        model.addAttribute("userId", user.getId());
        model.addAttribute("user", user);
        return "dashboard-customer";
    }
}

