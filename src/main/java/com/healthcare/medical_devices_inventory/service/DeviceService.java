package com.healthcare.medical_devices_inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.medical_devices_inventory.exception.ResourceNotFoundException;
import com.healthcare.medical_devices_inventory.model.Device;
import com.healthcare.medical_devices_inventory.model.DeviceCategory;
import com.healthcare.medical_devices_inventory.repository.DeviceCategoryRepository;
import com.healthcare.medical_devices_inventory.repository.DeviceRepository;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    private final DeviceCategoryRepository deviceCategoryRepository;

    public DeviceService(DeviceRepository deviceRepository,DeviceCategoryRepository deviceCategoryRepository){
        this.deviceRepository=deviceRepository;
        this.deviceCategoryRepository = deviceCategoryRepository;
    }

    @Transactional
    public Device createDevice(Device device){
         DeviceCategory category = deviceCategoryRepository.findById(device.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        device.setCategory(category);
        return deviceRepository.save(device);
    }

    public List<Device> getAllDevices(){
        return deviceRepository.findAll();
    }

    public Optional<Device> getDeviceById(Long deviceId){
        return deviceRepository.findById(deviceId);
    }

    public Optional<Device> update(Long deviceId,Device device){
        Optional<Device> deviceOpt = getDeviceById(deviceId);
        if(deviceOpt.isPresent()){
            Device existingDevice = deviceOpt.get();
            existingDevice.setModelNumber(device.getModelNumber());
            existingDevice.setSerialNumber(device.getSerialNumber());
            existingDevice.setDescription(device.getDescription());
            existingDevice.setQuantity(device.getQuantity());
            existingDevice.setRegistrationDate(device.getRegistrationDate());
            existingDevice.setMaintanenceDate(device.getMaintanenceDate());
            // existingDevice.setCreatedAt(device.getCreatedAt());
            // existingDevice.setUpdatedAt(device.getUpdatedAt());
            
            deviceRepository.save(existingDevice);
            return getDeviceById(deviceId);
        }
        return null;
    }

    public boolean deleteDevice(Long deviceId){
        Optional<Device> deviceOpt = getDeviceById(deviceId);
        if(deviceOpt.isPresent()){
            deviceRepository.deleteById(deviceId);
            return true;
        }
        return false;
    }
}
