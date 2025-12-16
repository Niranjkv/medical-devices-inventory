package com.healthcare.medical_devices_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.medical_devices_inventory.model.DeviceStatus;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long>{
    
}
