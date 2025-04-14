package org.example.booksy.page_controllers;

import jakarta.servlet.http.HttpSession;
import org.example.booksy.dto.AppointmentRequest;
import org.example.booksy.model.Appointment;
import org.example.booksy.model.User;
import org.example.booksy.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentPageController {

    private final AppointmentService appointmentService;

    public AppointmentPageController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/customer")
    public String viewCustomerAppointments(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null || user.getRole() != User.Role.CUSTOMER) {
            return "redirect:/users/login";
        }

        List<Appointment> appointments = appointmentService.getAppointmentsByCustomer(user.getId());
        model.addAttribute("appointments", appointments);
        return "appointments";
    }

    @GetMapping("/provider")
    public String providerAppointments(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        Long profileId = (Long) session.getAttribute("profileId");
        model.addAttribute("appointments", appointmentService.getAppointmentsByProvider(profileId));
        return "providerAppointments";
    }

    @GetMapping("/personal")
    public String personalAppointments(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("appointments", appointmentService.getAppointmentsByCustomer(user.getId()));
        return "appointments";
    }

    @GetMapping("/book")
    public String showBookingForm(@RequestParam Long serviceId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login";
        }

        AppointmentRequest request = new AppointmentRequest();
        request.setCustomerId(user.getId());
        request.setServiceId(serviceId);

        model.addAttribute("appointmentRequest", request);
        return "bookAppointment";
    }


    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute AppointmentRequest appointmentRequest,
                                  RedirectAttributes redirectAttributes) {
        appointmentService.bookAppointment(
                appointmentRequest.getCustomerId(),
                appointmentRequest.getServiceId(),
                appointmentRequest.getDateTime()
        );

        redirectAttributes.addFlashAttribute("message", "Appointment booked successfully!");
        return "redirect:/appointments/personal";
    }
}
