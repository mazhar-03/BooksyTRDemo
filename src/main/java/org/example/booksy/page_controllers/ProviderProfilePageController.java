package org.example.booksy.page_controllers;


import org.example.booksy.model.ServiceProviderProfile;
import org.example.booksy.service.ServiceProviderProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/providers")
public class ProviderProfilePageController {

    private final ServiceProviderProfileService profileService;

    public ProviderProfilePageController(ServiceProviderProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{userId}/profile-form")
    public String showProfileForm(@PathVariable Long userId, Model model) {
        model.addAttribute("profile", new ServiceProviderProfile());
        model.addAttribute("userId", userId);
        return "addProfile";
    }

    @PostMapping("/{userId}/profile")
    public String createProfile(@PathVariable Long userId,
                                @ModelAttribute ServiceProviderProfile profile,
                                Model model) {
        profileService.createProfile(userId, profile);
        model.addAttribute("message", "Profile created!");
        return "addProfile";
    }
}
