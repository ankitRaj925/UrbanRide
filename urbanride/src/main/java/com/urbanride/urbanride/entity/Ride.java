package com.urbanride.urbanride.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ride {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User rider;

    @ManyToOne
    private Driver driver;

    private Double originLat;
    private Double originLng;

    private Double destLat;
    private Double destLng;

    private String status;

    private Double fare;

    private Instant requestedAt = Instant.now();
    private Instant startedAt;
    private Instant endedAt;
}
