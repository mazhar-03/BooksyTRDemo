package org.example.booksy.controller;

import org.example.booksy.dto.AppointmentRequest;
import org.example.booksy.model.Appointment;
import org.example.booksy.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/book")
    public Appointment book(@RequestBody AppointmentRequest request) {
        return appointmentService.bookAppointment(
                request.getCustomerId(),
                request.getServiceId(),
                request.getDateTime()
        );
    }

    //List the customer's bookings
    @GetMapping("/customer/{customerId}")
    public List<Appointment> getByCustomer(@PathVariable Long customerId) {
        return appointmentService.getAppointmentsByCustomer(customerId);
    }

    //List the appointments for provider
    @GetMapping("/provider/{profileId}")
    public List<Appointment> getByProvider(@PathVariable Long profileId) {
        return appointmentService.getAppointmentsByProvider(profileId);
    }
}
