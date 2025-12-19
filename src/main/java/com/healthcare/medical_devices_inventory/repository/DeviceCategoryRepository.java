package com.healthcare.medical_devices_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.medical_devices_inventory.model.DeviceCategory;


@Repository
public interface  DeviceCategoryRepository extends JpaRepository<DeviceCategory, Long>{
    
}
