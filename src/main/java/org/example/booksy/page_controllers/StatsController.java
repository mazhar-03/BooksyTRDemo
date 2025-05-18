package org.example.booksy.page_controllers;


import jakarta.servlet.http.HttpSession;
import org.example.booksy.model.User;
import org.example.booksy.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Map;

@Controller
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/top-cities")
    public String showTopCities(Model model) {
        model.addAttribute("cityData", statsService.getTopCities());
        return "top-cities";
    }

    @GetMapping("/top-services")
    public String showTopServices(Model model) {
        model.addAttribute("serviceData", statsService.getTopServices());
        return "top-service";
    }

    @GetMapping("/monthly-revenue")
    public String monthlyRevenue(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals(User.Role.PROVIDER)) {
            return "redirect:/";
        }
        Map<String, Double> monthlyRevenue = statsService.getMonthlyRevenue(loggedInUser.getId());
        model.addAttribute("monthlyRevenue", monthlyRevenue);
        return "monthly-revenue";
    }

    @GetMapping("/income-overview")
    public String incomeOverview(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals(User.Role.PROVIDER)) {
            return "redirect:/";
        }
        double totalIncome = statsService.getTotalIncome(loggedInUser.getId());
        model.addAttribute("totalIncome", totalIncome);
        return "income-overview";
    }

}

