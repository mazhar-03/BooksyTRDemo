package org.example.booksy.page_controllers;

import org.example.booksy.model.ServiceItem;
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
                               Model model) {

        city = (city != null && !city.trim().isEmpty()) ? city : null;
        name = (name != null && !name.trim().isEmpty()) ? name : null;

        List<ServiceItem> services = serviceItemService.searchServices(city, name);
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
}
