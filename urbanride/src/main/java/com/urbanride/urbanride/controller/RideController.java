package com.urbanride.urbanride.controller;

import com.urbanride.urbanride.Dto.RideRequestDto;
import com.urbanride.urbanride.entity.Ride;
import com.urbanride.urbanride.entity.User;
import com.urbanride.urbanride.Repository.UserRepository;
import com.urbanride.urbanride.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController{
    private final RideService rideService;
    private final UserRepository userRepository;
    @PostMapping("/request")
    public ResponseEntity<?> requestRide(@RequestBody RideRequestDto dto){
        var user=userRepository.findById(UUID.fromString(dto.getRiderId())).orElseThrow();
        Ride r=rideService.requestRide(user,dto.getOriginLat(),dto.getOriginLng(),dto.getDestLat(),dto.getDestLng());
        return ResponseEntity.ok(r);
    }
    @PostMapping("/{id}/accept")
    public ResponseEntity<?> accept(@PathVariable String id,@RequestParam String driverId){return ResponseEntity.ok(rideService.acceptRide(id,driverId));}
    @PostMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable String id){return ResponseEntity.ok(rideService.completeRide(id));}
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id){return ResponseEntity.ok(rideService.rideRepo.findById(UUID.fromString(id)).orElseThrow());}
}

