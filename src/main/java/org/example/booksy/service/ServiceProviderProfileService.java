package org.example.booksy.service;

import org.example.booksy.exception.*;
import org.example.booksy.model.ServiceProviderProfile;
import org.example.booksy.model.User;
import org.example.booksy.repository.IServiceProviderProfileRepository;
import org.example.booksy.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceProviderProfileService {
    private final IUserRepository userRepository;
    private final IServiceProviderProfileRepository profileRepository;

    public ServiceProviderProfileService(IUserRepository userRepository, IServiceProviderProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    public ServiceProviderProfile createProfile(Long userId, ServiceProviderProfile profile) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if(user.getRole() != User.Role.PROVIDER)
            throw new NotProviderException();

        if(profileRepository.existsByUserId(userId))
            throw new ProfileAlreadyExistsException();

        profile.setUser(user);
        return profileRepository.save(profile);
    }

    public ServiceProviderProfile getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(ProfileNotFoundException::new);
    }
    public Optional<ServiceProviderProfile> findByUserId(Long userId) {
        return profileRepository.findByUserId(userId);
    }
}
