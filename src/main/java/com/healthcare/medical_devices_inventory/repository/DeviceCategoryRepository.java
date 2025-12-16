package com.healthcare.medical_devices_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.medical_devices_inventory.model.DeviceCategory;

public interface  DeviceCategoryRepository extends JpaRepository<DeviceCategory, Long>{
    
}
