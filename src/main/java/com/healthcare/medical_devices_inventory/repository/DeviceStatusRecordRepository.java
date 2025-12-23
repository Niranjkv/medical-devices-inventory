package com.healthcare.medical_devices_inventory.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.medical_devices_inventory.model.DeviceStatus;
import com.healthcare.medical_devices_inventory.model.DeviceStatusRecord;


@Repository
public interface DeviceStatusRecordRepository extends JpaRepository<DeviceStatusRecord, Long>{
    
    List<DeviceStatusRecord> findByDeviceId(Long deviceId);
    List<DeviceStatusRecord> findByStatusAndStatusChangeDateBetween(DeviceStatus status, LocalDateTime startDate, LocalDateTime endDate);
}
