package com.healthcare.medical_devices_inventory.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.medical_devices_inventory.model.DeviceStatus;
import com.healthcare.medical_devices_inventory.model.DeviceStatusRecord;


@Repository
public interface DeviceStatusRecordRepository extends JpaRepository<DeviceStatusRecord, Long>{
    
    Page<DeviceStatusRecord> findByDeviceId(Long deviceId, Pageable pageable);
    Optional<DeviceStatusRecord> findTop1ByDeviceIdOrderByStatusChangeDateDesc(Long deviceId);
    List<DeviceStatusRecord> findByStatusAndStatusChangeDateBetween(DeviceStatus status, LocalDateTime startDate, LocalDateTime endDate);
}
