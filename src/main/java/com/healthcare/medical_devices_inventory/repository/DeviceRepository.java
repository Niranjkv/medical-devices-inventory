package com.healthcare.medical_devices_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.medical_devices_inventory.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Long>{
    
}
