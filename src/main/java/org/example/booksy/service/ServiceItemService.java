package org.example.booksy.service;

import org.example.booksy.exception.ProfileNotFoundException;
import org.example.booksy.model.ServiceItem;
import org.example.booksy.model.ServiceProviderProfile;
import org.example.booksy.repository.IServiceItemRepository;
import org.example.booksy.repository.IServiceProviderProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceItemService {
    private final IServiceProviderProfileRepository profileRepository;
    private final IServiceItemRepository itemRepository;

    public ServiceItemService(IServiceProviderProfileRepository profileRepository, IServiceItemRepository itemRepository) {
        this.profileRepository = profileRepository;
        this.itemRepository = itemRepository;
    }

    public ServiceItem createService(Long providedProfileId, ServiceItem serviceItem) {
        ServiceProviderProfile profile = profileRepository.findById(providedProfileId)
                .orElseThrow(ProfileNotFoundException::new);

        serviceItem.setProviderProfile(profile);
        return itemRepository.save(serviceItem);
    }

    public List<ServiceItem> getServicesByProvider(Long profileId) {
        return itemRepository.findByProviderProfile_Id(profileId);
    }

    public List<ServiceItem> searchServices(String city, String name) {
        return itemRepository.searchServices(city, name);
    }

    public List<ServiceItem> searchServices(String city, String name, Long excludeUserId) {
        List<ServiceItem> all = itemRepository.searchServices(city, name);
        if (excludeUserId == null) return all;

        return all.stream()
                .filter(s -> s.getProviderProfile().getUser().getId().equals(excludeUserId))
                .toList();
    }

}
