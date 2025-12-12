package com.urbanride.urbanride.service;

import com.urbanride.urbanride.entity.Driver;
import com.urbanride.urbanride.Repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverService{
    private final DriverRepository repo;
    public Driver save(Driver d){return repo.save(d);}
    public List<Driver> getAvailable(){return repo.findByIsAvailableTrue();}
    public Driver updateLocation(String id,Double lat,Double lng){
        var d=repo.findById(UUID.fromString(id)).orElseThrow();
        d.setCurrentLat(lat);
        d.setCurrentLng(lng);
        return repo.save(d);
    }
}

