package com.healthcare.medical_devices_inventory.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.healthcare.medical_devices_inventory.exception.ResourceNotFoundException;
import com.healthcare.medical_devices_inventory.model.Device;
import com.healthcare.medical_devices_inventory.model.DeviceStatus;
import com.healthcare.medical_devices_inventory.model.DeviceStatusRecord;
import com.healthcare.medical_devices_inventory.repository.DeviceRepository;
import com.healthcare.medical_devices_inventory.repository.DeviceStatusRecordRepository;

@Service
public class DeviceStatusRecordService{

    private final DeviceStatusRecordRepository deviceStatusRecordRepository;
    private final DeviceRepository deviceRepository;

    public DeviceStatusRecordService(DeviceStatusRecordRepository deviceStatusRecordRepository,DeviceRepository deviceRepository){
        this.deviceStatusRecordRepository = deviceStatusRecordRepository;
        this.deviceRepository = deviceRepository;
    }

    //Create New DeviceStatusRecord
       public DeviceStatusRecord createDeviceStatusRecord(Long deviceId, DeviceStatus status) {

        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id " + deviceId));

        // Create a new DeviceStatusRecord
        DeviceStatusRecord statusRecord = new DeviceStatusRecord();
        statusRecord.setDevice(device);
        statusRecord.setStatus(status);
        statusRecord.setStatusChangeDate(LocalDateTime.now());  
        statusRecord.setCreatedAt(LocalDateTime.now());      
        statusRecord.setUpdatedAt(LocalDateTime.now());

        // Save the status record
        return deviceStatusRecordRepository.save(statusRecord);
    }

     public DeviceStatusRecord getDeviceStatusRecordById(Long recordId) {
        return deviceStatusRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Device Status Record not found with id " + recordId));
    }

     public List<DeviceStatusRecord> getDeviceStatusRecordsByDeviceId(Long deviceId) {
        return deviceStatusRecordRepository.findByDeviceId(deviceId);
    }
    public DeviceStatusRecord updateDeviceStatusRecord(Long recordId, DeviceStatusRecord updatedRecord) {
        // Check if the record exists
        DeviceStatusRecord existingRecord = deviceStatusRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Device Status Record not found with id " + recordId));

        // Update existing record
        existingRecord.setStatus(updatedRecord.getStatus());
        existingRecord.setStatusChangeDate(updatedRecord.getStatusChangeDate());
        existingRecord.setUpdatedAt(LocalDateTime.now()); 

        // Save and return the updated record
        return deviceStatusRecordRepository.save(existingRecord);
    }

    // Get the Latest Status Record of a Device
    public DeviceStatusRecord getLatestDeviceStatus(Long deviceId) {
        List<DeviceStatusRecord> records = deviceStatusRecordRepository.findByDeviceId(deviceId);
        if (records.isEmpty()) {
            throw new ResourceNotFoundException("No status records found for device with id " + deviceId);
        }
        records.sort(Comparator.comparing(DeviceStatusRecord::getStatusChangeDate).reversed());
        return records.get(0);
    }

    // Get all Device Status Records
    public List<DeviceStatusRecord> getAllDeviceStatusRecords() {
        return deviceStatusRecordRepository.findAll();
    }

    // Search for Device Status Records by status and date range
    public List<DeviceStatusRecord> searchDeviceStatusRecords(DeviceStatus status, LocalDateTime startDate, LocalDateTime endDate) {
        return deviceStatusRecordRepository.findByStatusAndStatusChangeDateBetween(status, startDate, endDate);
    }
}