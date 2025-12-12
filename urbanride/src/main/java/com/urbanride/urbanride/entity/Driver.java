package com.urbanride.urbanride.entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private User user;

    private String vehicleType;

    private Double currentLat;

    private Double currentLng;

    private Boolean isAvailable = true;

    private Double rating = 0.0;
}
