package com.healthcare.medical_devices_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.medical_devices_inventory.model.DeviceStatusRecord;

public interface DeviceStatusRecordRepository extends JpaRepository<DeviceStatusRecord, Long>{
    
}
