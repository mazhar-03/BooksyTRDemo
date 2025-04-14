package org.example.booksy.repository;

import org.example.booksy.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAppointmentRepository extends CrudRepository<Appointment, Long> {
    List<Appointment> findByCustomer_IdOrderByDateTimeAsc(Long customerId);
    List<Appointment> findByService_ProviderProfile_IdOrderByDateTimeAsc(Long providerId);

}
