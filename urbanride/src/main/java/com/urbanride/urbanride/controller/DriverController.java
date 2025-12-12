package com.urbanride.urbanride.controller;

import com.urbanride.urbanride.Dto.LocationUpdate;
import com.urbanride.urbanride.entity.Driver;
import com.urbanride.urbanride.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController{
    private final DriverService service;
    @PostMapping("/{id}/location")
    public ResponseEntity<?> updateLocation(@PathVariable String id,@RequestBody LocationUpdate loc){
        return ResponseEntity.ok(service.updateLocation(id,loc.getLat(),loc.getLng()));
    }
    @GetMapping("/available")
    public ResponseEntity<?> available(){return ResponseEntity.ok(service.getAvailable());}
}
