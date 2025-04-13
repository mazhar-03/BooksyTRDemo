package org.example.booksy.controller;

import org.example.booksy.model.ServiceItem;
import org.example.booksy.service.ServiceItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceItemController {
    private final ServiceItemService serviceItemService;

    public ServiceItemController(ServiceItemService serviceItemService) {
        this.serviceItemService = serviceItemService;
    }

    @PostMapping("/provider/{profileId}")
    public ServiceItem createService(@PathVariable Long profileId,
                                     @RequestBody ServiceItem serviceItem) {
        return serviceItemService.createService(profileId, serviceItem);
    }

    @GetMapping("/provider/{profileId}")
    public List<ServiceItem> getServices(@PathVariable Long profileId) {
        return serviceItemService.getServicesByProvider(profileId);
    }

    @GetMapping("/search")
    public List<ServiceItem> searchByCityAndName(@RequestParam(required = false) String city ,
                                                 @RequestParam(required = false) String name) {
        return serviceItemService.searchServices(city, name);
    }

}
