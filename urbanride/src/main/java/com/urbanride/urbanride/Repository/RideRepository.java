package com.urbanride.urbanride.Repository;



import com.urbanride.urbanride.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {}
