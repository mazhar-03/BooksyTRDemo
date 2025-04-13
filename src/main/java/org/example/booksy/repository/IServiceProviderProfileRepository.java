package org.example.booksy.repository;

import org.example.booksy.model.ServiceProviderProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IServiceProviderProfileRepository extends CrudRepository<ServiceProviderProfile, Long> {
    boolean existsByUserId(Long userId);
    Optional<ServiceProviderProfile> findByUserId(Long userId);
}
