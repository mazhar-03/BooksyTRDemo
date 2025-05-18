package org.example.booksy.repository;

import org.example.booksy.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAppointmentRepository extends CrudRepository<Appointment, Long> {
    List<Appointment> findByCustomer_IdOrderByDateTimeAsc(Long customerId);
    List<Appointment> findByService_ProviderProfile_IdOrderByDateTimeAsc(Long providerId);

    @Query("SELECT a.service.providerProfile.city, COUNT(a) FROM Appointment a GROUP BY a.service.providerProfile.city")
    List<Object[]> countAppointmentsByCity();

    @Query("SELECT a.service.name, COUNT(a) FROM Appointment a GROUP BY a.service.name")
    List<Object[]> countAppointmentsByService();

    @Query("SELECT FUNCTION('TO_CHAR', a.dateTime, 'YYYY-MM'), SUM(a.service.price) " +
            "FROM Appointment a " +
            "WHERE a.service.providerProfile.user.id = :providerUserId AND a.status = 'COMPLETED' " +
            "GROUP BY FUNCTION('TO_CHAR', a.dateTime, 'YYYY-MM') " +
            "ORDER BY FUNCTION('TO_CHAR', a.dateTime, 'YYYY-MM')")
    List<Object[]> findMonthlyRevenueByProvider(@Param("providerUserId") Long providerUserId);

    @Query("SELECT SUM(a.service.price) " +
            "FROM Appointment a " +
            "WHERE a.service.providerProfile.user.id = :providerUserId AND a.status = 'COMPLETED'")
    Double findTotalIncomeByProvider(@Param("providerUserId") Long providerUserId);


}
