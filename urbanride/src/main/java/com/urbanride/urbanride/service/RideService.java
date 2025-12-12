package com.urbanride.urbanride.service;

import com.urbanride.urbanride.entity.Ride;
import com.urbanride.urbanride.entity.Driver;
import com.urbanride.urbanride.entity.User;
import com.urbanride.urbanride.Repository.RideRepository;
import com.urbanride.urbanride.Repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import com.urbanride.urbanride.util.Haversine;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideService{
    public final RideRepository rideRepo;
    private final DriverRepository driverRepo;
    public Ride requestRide(User rider,Double oLat,Double oLng,Double dLat,Double dLng){
        List<Driver> available=driverRepo.findByIsAvailableTrue();
        Driver nearest=available.stream().min(Comparator.comparingDouble(dr->Haversine.distance(oLat,oLng,dr.getCurrentLat()==null?0:dr.getCurrentLat(),dr.getCurrentLng()==null?0:dr.getCurrentLng()))).orElseThrow();
        nearest.setIsAvailable(false);
        driverRepo.save(nearest);
        var ride=Ride.builder().rider(rider).driver(nearest).originLat(oLat).originLng(oLng).destLat(dLat).destLng(dLng).status("REQUESTED").fare(calcFare(oLat,oLng,dLat,dLng)).requestedAt(Instant.now()).build();
        return rideRepo.save(ride);
    }
    public Ride acceptRide(String rideId,String driverId){
        var r=rideRepo.findById(UUID.fromString(rideId)).orElseThrow();
        if(!r.getDriver().getId().equals(driverId))throw new RuntimeException("Not allowed");
        r.setStatus("ACCEPTED");
        r.setStartedAt(Instant.now());
        return rideRepo.save(r);
    }
    public Ride completeRide(String rideId){
        var r=rideRepo.findById(UUID.fromString(rideId)).orElseThrow();
        r.setStatus("COMPLETED");
        r.setEndedAt(Instant.now());
        var drv=r.getDriver();
        drv.setIsAvailable(true);
        driverRepo.save(drv);
        return rideRepo.save(r);
    }
    private double calcFare(double oLat,double oLng,double dLat,double dLng){
        double km=Haversine.distance(oLat,oLng,dLat,dLng);
        return 30+km*10;
    }
}
