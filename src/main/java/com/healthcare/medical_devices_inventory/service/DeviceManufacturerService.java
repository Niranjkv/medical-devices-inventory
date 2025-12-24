package com.healthcare.medical_devices_inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.healthcare.medical_devices_inventory.model.DeviceManufacturer;
import com.healthcare.medical_devices_inventory.repository.DeviceManufacturerRepository;

@Service
public class DeviceManufacturerService {
    
    private final DeviceManufacturerRepository deviceManufacturerRepository;

    public DeviceManufacturerService(DeviceManufacturerRepository deviceManufacturerRepository){
        this.deviceManufacturerRepository = deviceManufacturerRepository;
    }

    // Get all manufacturers
    public List<DeviceManufacturer> getAllManufacturers(){
        return deviceManufacturerRepository.findAll();
    }

    // Get manufacturer by id
    public Optional<DeviceManufacturer> getManufacturerById(Long manufacturerId){
        return deviceManufacturerRepository.findById(manufacturerId);
    }

    // Create a new manufacturer
    public DeviceManufacturer createManufacturer(DeviceManufacturer deviceManufacturer){
        return deviceManufacturerRepository.save(deviceManufacturer);
    }

    // Update an existing manufacturer
    public DeviceManufacturer updateManufacturer(Long id, DeviceManufacturer deviceManufacturer){
        Optional<DeviceManufacturer> existingManufacturer = getManufacturerById(id);
        if(existingManufacturer.isPresent()){
            DeviceManufacturer existing = existingManufacturer.get();
            existing.setName(deviceManufacturer.getName());
            existing.setContactEmail(deviceManufacturer.getContactEmail());
            existing.setContactPhone(deviceManufacturer.getContactPhone());
            existing.setCreatedAt(deviceManufacturer.getCreatedAt());
            existing.setUpdatedAt(deviceManufacturer.getUpdatedAt());
            deviceManufacturerRepository.save(existing);
            return existing; // return updated manufacturer
        }
        return null; // return null if manufacturer not found
    }

    public List<DeviceManufacturer> getFilteredManufacturers(String name) {
        if (name != null && !name.isBlank()) {
            return deviceManufacturerRepository.findByNameContainingIgnoreCase(name);
        }
        return deviceManufacturerRepository.findAll();
    }

    
    // Delete a manufacturer
    public boolean deleteManufacturer(Long manufacturerId){
        Optional<DeviceManufacturer> existingManufacturer = getManufacturerById(manufacturerId);
        if(existingManufacturer.isPresent()){
            deviceManufacturerRepository.deleteById(manufacturerId);
            return true; // return true if deletion was successful
        }
        return false; // return false if manufacturer not found
    }
}
