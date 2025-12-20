package com.healthcare.medical_devices_inventory.repository;

import com.healthcare.medical_devices_inventory.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>{
    
}
