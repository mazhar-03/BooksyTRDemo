package org.example.booksy.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private double price;
    private int durationMinutes;

    @ManyToOne
    @JoinColumn(name = "provider_profile_id", nullable = false)
    private ServiceProviderProfile providerProfile;
}
