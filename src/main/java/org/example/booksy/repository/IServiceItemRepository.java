package org.example.booksy.repository;

import org.example.booksy.model.ServiceItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IServiceItemRepository extends CrudRepository<ServiceItem, Long> {
    List<ServiceItem> findByProviderProfile_Id(Long providerProfileId);

    @Query("SELECT s FROM ServiceItem s WHERE " +
            "(:city IS NULL OR LOWER(s.providerProfile.city) = LOWER(:city)) AND " +
            "(:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<ServiceItem> searchServices(@Param("city") String city,
                                     @Param("name") String name);
}

