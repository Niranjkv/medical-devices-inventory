package com.healthcare.medical_devices_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.medical_devices_inventory.model.DeviceLocation;

@Repository
public interface DeviceLocationRepository extends JpaRepository<DeviceLocation,Long>{
    
}
