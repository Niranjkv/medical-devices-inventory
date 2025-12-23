package com.healthcare.medical_devices_inventory.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //Create new DeviceStatusRecord
    @Transactional
       public DeviceStatusRecord createDeviceStatusRecord(Long deviceId, DeviceStatus status) {

        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id " + deviceId));

        // Create a new DeviceStatusRecord Object
        DeviceStatusRecord statusRecord = new DeviceStatusRecord();
        statusRecord.setDevice(device);
        statusRecord.setStatus(status);
        statusRecord.setStatusChangeDate(LocalDateTime.now());  
        statusRecord.setCreatedAt(LocalDateTime.now());      
        statusRecord.setUpdatedAt(LocalDateTime.now());

        // Save the status record
        return deviceStatusRecordRepository.save(statusRecord);
    }

    @Transactional(readOnly = true)
     public DeviceStatusRecord getDeviceStatusRecordById(Long recordId) {
        return deviceStatusRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Device Status Record not found with id " + recordId));
    }

    @Transactional(readOnly = true)
    public Page<DeviceStatusRecord> getDeviceStatusRecordsByDeviceId(Long deviceId,int page, int size) {
         PageRequest pageRequest = PageRequest.of(page, size);
        return deviceStatusRecordRepository.findByDeviceId(deviceId, pageRequest);
    }

    @Transactional
    public DeviceStatusRecord updateDeviceStatusRecord(Long recordId, DeviceStatusRecord updatedRecord) {
        // Check if the record exists
        DeviceStatusRecord existingRecord = deviceStatusRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Device Status Record not found with id " + recordId));

        // Update existing record
        existingRecord.setStatus(updatedRecord.getStatus());
        existingRecord.setStatusChangeDate(LocalDateTime.now());
       // existingRecord.setUpdatedAt(LocalDateTime.now()); 

        // Save and return the updated record
        return deviceStatusRecordRepository.save(existingRecord);
    }

    @Transactional(readOnly = true)
    // Get the Latest Status Record of a Device
    public DeviceStatusRecord getLatestDeviceStatus(Long deviceId) {
        Optional<DeviceStatusRecord> record = deviceStatusRecordRepository.findTop1ByDeviceIdOrderByStatusChangeDateDesc(deviceId);
        return record.orElseThrow(() -> new ResourceNotFoundException("No status records found for device with id " + deviceId));
    }

    // Get all Device Status Records
    @Transactional(readOnly = true)
    public List<DeviceStatusRecord> getAllDeviceStatusRecords() {
        return deviceStatusRecordRepository.findAll();
    }

    @Transactional(readOnly = true)
    // Search for Device Status Records by status and date range
    public List<DeviceStatusRecord> searchDeviceStatusRecords(DeviceStatus status, LocalDateTime startDate, LocalDateTime endDate) {
        return deviceStatusRecordRepository.findByStatusAndStatusChangeDateBetween(status, startDate, endDate);
    }

    @Transactional
    public void deleteDeviceStatusRecord(Long recordId) {
        // Check if the record exists
        if (!deviceStatusRecordRepository.existsById(recordId)) {
            throw new ResourceNotFoundException("Device Status Record not found with id " + recordId);
        }
        // Delete the record if it exists
        deviceStatusRecordRepository.deleteById(recordId);
    }
}