package com.healthcare.medical_devices_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.medical_devices_inventory.model.DeviceManufacturer;

@Repository
public interface DeviceManufacturerRepository extends JpaRepository<DeviceManufacturer,Long>{
    
}
