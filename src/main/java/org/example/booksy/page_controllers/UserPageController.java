package org.example.booksy.page_controllers;

import jakarta.servlet.http.HttpSession;
import org.example.booksy.dto.LoginRequest;
import org.example.booksy.model.User;
import org.example.booksy.service.ServiceProviderProfileService;
import org.example.booksy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserPageController {

    private final UserService userService;
    private final ServiceProviderProfileService providerProfileService;

    public UserPageController(UserService userService, ServiceProviderProfileService providerProfileService) {
        this.userService = userService;
        this.providerProfileService = providerProfileService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginRequest") LoginRequest loginRequest,
                        HttpSession session,
                        Model model) {
        try {
            User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            session.setAttribute("loggedInUser", user);

            if (user.getRole() == User.Role.PROVIDER) {
                return providerProfileService.findByUserId(user.getId())
                        .map(profile -> {
                            session.setAttribute("profileId", profile.getId());
                            return "redirect:/dashboard/provider";
                        })
                        .orElse("redirect:/providers/create-profile");
            } else {
                return "redirect:/dashboard/customer";
            }

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        userService.register(user);
        model.addAttribute("message", "Registration successful!");
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("logoutSuccess", "You have been logged out successfully.");
        return "redirect:/home";
    }

}
