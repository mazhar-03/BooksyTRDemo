package org.example.booksy.page_controllers;

import org.example.booksy.dto.AppointmentRequest;
import org.example.booksy.model.Appointment;
import org.example.booksy.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentPageController {

    private final AppointmentService appointmentService;

    public AppointmentPageController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/book-form")
    public String showBookingForm(Model model) {
        model.addAttribute("appointmentRequest", new AppointmentRequest());
        return "bookAppointment";
    }

    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute AppointmentRequest request, Model model) {
        appointmentService.bookAppointment(request.getCustomerId(), request.getServiceId(), request.getDateTime());
        model.addAttribute("message", "Appointment booked!");
        model.addAttribute("appointmentRequest", new AppointmentRequest());
        return "bookAppointment";
    }

    @GetMapping("/customer/{customerId}")
    public String viewAppointments(@PathVariable Long customerId, Model model) {
        List<Appointment> appointments = appointmentService.getAppointmentsByCustomer(customerId);
        model.addAttribute("appointments", appointments);
        return "appointments";
    }

    @GetMapping("/provider/{providerId}")
    public String viewProviderAppointments(@PathVariable Long providerId, Model model) {
        List<Appointment> appointments = appointmentService.getAppointmentsByProvider(providerId);
        model.addAttribute("appointments", appointments);
        return "providerAppointments"; // maps to templates/providerAppointments.html
    }

}
