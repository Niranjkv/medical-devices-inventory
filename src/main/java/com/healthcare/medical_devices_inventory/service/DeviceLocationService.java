package com.healthcare.medical_devices_inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.medical_devices_inventory.dto.DeviceDTO;
import com.healthcare.medical_devices_inventory.dto.DeviceLocationDTO;
import com.healthcare.medical_devices_inventory.exception.ResourceNotFoundException;
import com.healthcare.medical_devices_inventory.model.Device;
import com.healthcare.medical_devices_inventory.model.DeviceLocation;
import com.healthcare.medical_devices_inventory.repository.DeviceLocationRepository;
import com.healthcare.medical_devices_inventory.repository.DeviceRepository;

@Service
@Transactional
public class DeviceLocationService {

    private final DeviceLocationRepository locationRepository;
    private final DeviceRepository deviceRepository;

    public DeviceLocationService(DeviceLocationRepository locationRepository, DeviceRepository deviceRepository) {
        this.locationRepository = locationRepository;
        this.deviceRepository = deviceRepository;
    }


    public DeviceLocationDTO create(DeviceLocation location) {
        return mapToDTO(locationRepository.save(location));
    }

    public DeviceLocationDTO update(Long id, DeviceLocation input) {
        DeviceLocation location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        location.setLocationName(input.getLocationName());
        location.setLocationType(input.getLocationType());
        location.setDescription(input.getDescription());

        return mapToDTO(locationRepository.save(location));
    }

    public DeviceLocationDTO getById(Long id) {
        return mapToDTO(
                locationRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Location not found"))
        );
    }

    public List<DeviceLocationDTO> getAll() {
        return locationRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public void delete(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Location not found");
        }
        locationRepository.deleteById(id);
    }

     public List<DeviceDTO> getDevicesByLocation(Long locationId) {
        return deviceRepository.findByLocationId(locationId)
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

    private DeviceLocationDTO mapToDTO(DeviceLocation location) {
        DeviceLocationDTO dto = new DeviceLocationDTO();
        dto.setId(location.getId());
        dto.setLocationName(location.getLocationName());
        dto.setLocationType(location.getLocationType());
        return dto;
    }
}
