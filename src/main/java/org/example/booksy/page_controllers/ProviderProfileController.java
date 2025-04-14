package org.example.booksy.page_controllers;

import jakarta.servlet.http.HttpSession;
import org.example.booksy.model.ServiceProviderProfile;
import org.example.booksy.model.User;
import org.example.booksy.service.ServiceProviderProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// KEEP THIS CLASS
@Controller
@RequestMapping("/providers")
public class ProviderProfileController {

    private final ServiceProviderProfileService providerProfileService;

    public ProviderProfileController(ServiceProviderProfileService service) {
        this.providerProfileService = service;
    }

    @GetMapping("/create-profile")
    public String createProfileForm(Model model, HttpSession session) {
        model.addAttribute("profile", new ServiceProviderProfile());
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/users/login";
        model.addAttribute("userId", user.getId());
        return "addProfile";
    }

    @PostMapping("/{userId}/profile")
    public String createProfile(@PathVariable Long userId,
                                @ModelAttribute ServiceProviderProfile profile,
                                HttpSession session) {
        ServiceProviderProfile created = providerProfileService.createProfile(userId, profile);
        session.setAttribute("profileId", created.getId());
        return "redirect:/dashboard/provider";
    }
}

