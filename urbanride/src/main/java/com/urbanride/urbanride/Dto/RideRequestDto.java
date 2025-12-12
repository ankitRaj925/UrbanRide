package com.urbanride.urbanride.Dto;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto{
    private String riderId;
    private Double originLat;
    private Double originLng;
    private Double destLat;
    private Double destLng;
}
