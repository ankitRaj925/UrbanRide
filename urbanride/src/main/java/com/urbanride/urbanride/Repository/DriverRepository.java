package com.urbanride.urbanride.Repository;


import com.urbanride.urbanride.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
    List<Driver> findByIsAvailableTrue();
}

