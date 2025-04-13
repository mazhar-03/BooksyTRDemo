package org.example.booksy.controller;

import org.example.booksy.model.ServiceProviderProfile;
import org.example.booksy.service.ServiceProviderProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/providers")
public class ServiceProviderProfileController {
    private final ServiceProviderProfileService profileService;

    public ServiceProviderProfileController(ServiceProviderProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/{userId}/profile")
    public ServiceProviderProfile createProfile(@PathVariable Long userId,
                                                @RequestBody ServiceProviderProfile profile) {

        return profileService.createProfile(userId, profile);
    }

    @GetMapping("/{userId}/profile")
    public ServiceProviderProfile getProfile(@PathVariable Long userId) {
        return profileService.getProfileByUserId(userId);
    }

}
