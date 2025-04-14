package org.example.booksy.page_controllers;

import org.example.booksy.dto.LoginRequest;
import org.example.booksy.model.User;
import org.example.booksy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserPageController {

    private final UserService userService;

    public UserPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        System.out.println("📥 Incoming User from Form: " + user);
        try {
            userService.register(user);
            model.addAttribute("message", "User registered successfully!");
            model.addAttribute("user", new User()); // clear form
        } catch (Exception e) {
            model.addAttribute("message", "Registration failed: " + e.getMessage());
        }
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute LoginRequest loginRequest, Model model) {
        try {
            User loggedInUser = userService.login(loginRequest);
            model.addAttribute("message", "Login successful. Welcome, " + loggedInUser.getFullName() + "!");
        } catch (Exception e) {
            model.addAttribute("message", "Login failed: " + e.getMessage());
        }
        return "login";
    }
}
