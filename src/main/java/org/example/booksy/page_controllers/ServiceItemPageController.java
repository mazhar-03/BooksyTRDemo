package org.example.booksy.page_controllers;

import jakarta.servlet.http.HttpSession;
import org.example.booksy.model.ServiceItem;
import org.example.booksy.model.User;
import org.example.booksy.service.ServiceItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/services")
public class ServiceItemPageController {

    private final ServiceItemService serviceItemService;

    public ServiceItemPageController(ServiceItemService serviceItemService) {
        this.serviceItemService = serviceItemService;
    }

    @GetMapping("/view")
    public String viewServices(@RequestParam(required = false) String city,
                               @RequestParam(required = false) String name,
                               HttpSession session,
                               Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        Long excludeUserId = user != null ? user.getId() : null;

        List<ServiceItem> services = serviceItemService.searchServices(city, name, excludeUserId);
        model.addAttribute("services", services);
        return "services";
    }

    @GetMapping("/provider/{profileId}/add-form")
    public String showAddServiceForm(@PathVariable Long profileId, Model model) {
        model.addAttribute("service", new ServiceItem());
        model.addAttribute("profileId", profileId);
        return "addService";
    }

    @PostMapping("/provider/{profileId}")
    public String addService(@PathVariable Long profileId,
                             @ModelAttribute ServiceItem service,
                             RedirectAttributes redirectAttributes) {
        serviceItemService.createService(profileId, service);
        redirectAttributes.addFlashAttribute("message", "Service added successfully!");
        return "redirect:/services/provider/" + profileId + "/add-form";
    }

    @GetMapping("/provider/my-services")
    public String viewMyServices(HttpSession session, Model model) {
        Long profileId = (Long) session.getAttribute("profileId");
        if (profileId == null)
            return "redirect:/users/login";

        List<ServiceItem> services = serviceItemService.getServicesByProvider(profileId);
        model.addAttribute("services", services);
        return "providerServices";
    }
}
