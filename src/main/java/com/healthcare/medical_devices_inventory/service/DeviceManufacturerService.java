package com.healthcare.medical_devices_inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.medical_devices_inventory.dto.DeviceDTO;
import com.healthcare.medical_devices_inventory.dto.DeviceManufacturerDTO;
import com.healthcare.medical_devices_inventory.exception.ResourceNotFoundException;
import com.healthcare.medical_devices_inventory.model.Device;
import com.healthcare.medical_devices_inventory.model.DeviceManufacturer;
import com.healthcare.medical_devices_inventory.repository.DeviceManufacturerRepository;
import com.healthcare.medical_devices_inventory.repository.DeviceRepository;

@Service
@Transactional
public class DeviceManufacturerService {

    private final DeviceManufacturerRepository manufacturerRepository;
    private final DeviceRepository deviceRepository;

    public DeviceManufacturerService(DeviceManufacturerRepository manufacturerRepository, DeviceRepository deviceRepository) {
        this.manufacturerRepository = manufacturerRepository;
        this.deviceRepository = deviceRepository;
    }

    public DeviceManufacturerDTO create(DeviceManufacturer manufacturer) {
        return mapToDTO(manufacturerRepository.save(manufacturer));
    }

    public List<DeviceManufacturerDTO> getAll() {
        return manufacturerRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public DeviceManufacturerDTO getById(Long id) {
        DeviceManufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer not found"));
        return mapToDTO(manufacturer);
    }

    public DeviceManufacturerDTO update(Long id, DeviceManufacturer input) {
        DeviceManufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer not found"));

        manufacturer.setName(input.getName());
        manufacturer.setContactEmail(input.getContactEmail());
        manufacturer.setContactPhone(input.getContactPhone());

        return mapToDTO(manufacturerRepository.save(manufacturer));
    }

    public void delete(Long id) {
        if (!manufacturerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Manufacturer not found");
        }
        manufacturerRepository.deleteById(id);
    }
    public List<DeviceDTO> getDevicesByManufacturer(Long manufacturerId) {
        return deviceRepository.findByManufacturerId(manufacturerId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private DeviceDTO mapToDTO(Device device) {
        DeviceDTO dto = new DeviceDTO();
        dto.setId(device.getId());
        dto.setName(device.getName());
        dto.setModelNumber(device.getModelNumber());
        dto.setSerialNumber(device.getSerialNumber());
        dto.setDescription(device.getDescription());
        dto.setQuantity(device.getQuantity());
        dto.setRegistrationDate(device.getRegistrationDate());
        dto.setMaintanenceDate(device.getMaintanenceDate());
        dto.setCategoryId(device.getCategory().getId());
        dto.setLocationId(device.getLocation().getId());
        dto.setManufacturerId(device.getManufacturer().getId());
        return dto;
    }
    
    private DeviceManufacturerDTO mapToDTO(DeviceManufacturer manufacturer) {
        DeviceManufacturerDTO dto = new DeviceManufacturerDTO();
        dto.setId(manufacturer.getId());
        dto.setName(manufacturer.getName());
        dto.setContactEmail(manufacturer.getContactEmail());
        dto.setContactPhone(manufacturer.getContactPhone());
        return dto;
    }
}
