package org.example.booksy.service;

import jakarta.transaction.Transactional;
import org.example.booksy.model.Appointment;
import org.example.booksy.model.AppointmentStatus;
import org.example.booksy.model.ServiceItem;
import org.example.booksy.model.User;
import org.example.booksy.repository.IAppointmentRepository;
import org.example.booksy.repository.IServiceItemRepository;
import org.example.booksy.repository.IUserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    private final IAppointmentRepository appointmentRepository;
    private final IUserRepository userRepository;
    private final IServiceItemRepository itemRepository;

    public AppointmentService(IAppointmentRepository appointmentRepository, IUserRepository userRepository, IServiceItemRepository itemRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public Appointment bookAppointment(Long customerId, Long serviceId, LocalDateTime dateTime) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        ServiceItem service = itemRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Appointment appointment = Appointment.builder()
                .customer(customer)
                .service(service)
                .dateTime(dateTime)
                .status(AppointmentStatus.BOOKED)
                .build();

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByProvider(Long providerId) {
        return appointmentRepository.findByService_ProviderProfile_IdOrderByDateTimeAsc(providerId);
    }

    public List<Appointment> getAppointmentsByCustomer(Long customerId) {
        return appointmentRepository.findByCustomer_IdOrderByDateTimeAsc(customerId);
    }

    // no need to put in a controller it is a background task
    @Scheduled(fixedRate = 60 * 60 * 1000 * 24) // every 24h
    @Transactional
    public void autoCompleteAppointments() {
        List<Appointment> overdue = appointmentRepository.findOverdueAppointments(LocalDateTime.now());
        for (Appointment a : overdue) {
            a.setStatus(AppointmentStatus.COMPLETED);
        }
        appointmentRepository.saveAll(overdue);
    }
}
