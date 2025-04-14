package org.example.booksy.service;

import org.example.booksy.model.Appointment;
import org.example.booksy.model.ServiceItem;
import org.example.booksy.model.User;
import org.example.booksy.repository.IAppointmentRepository;
import org.example.booksy.repository.IServiceItemRepository;
import org.example.booksy.repository.IUserRepository;
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
                .status("BOOKED")
                .build();

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByProvider(Long providerId) {
        return appointmentRepository.findByService_ProviderProfile_IdOrderByDateTimeAsc(providerId);
    }

    public List<Appointment> getAppointmentsByCustomer(Long customerId) {
        return appointmentRepository.findByCustomer_IdOrderByDateTimeAsc(customerId);
    }
}
