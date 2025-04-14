package org.example.booksy.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private Long customerId;
    private Long serviceId;
    private LocalDateTime dateTime;
}
