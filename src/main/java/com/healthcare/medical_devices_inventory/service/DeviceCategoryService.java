package com.healthcare.medical_devices_inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.medical_devices_inventory.dto.DeviceCategoryDTO;
import com.healthcare.medical_devices_inventory.dto.DeviceDTO;
import com.healthcare.medical_devices_inventory.exception.ResourceNotFoundException;
import com.healthcare.medical_devices_inventory.model.Device;
import com.healthcare.medical_devices_inventory.model.DeviceCategory;
import com.healthcare.medical_devices_inventory.repository.DeviceCategoryRepository;
import com.healthcare.medical_devices_inventory.repository.DeviceRepository;

@Service
@Transactional
public class DeviceCategoryService {

    private final DeviceCategoryRepository categoryRepository;
    private final DeviceRepository deviceRepository;

    public DeviceCategoryService(DeviceCategoryRepository categoryRepository, DeviceRepository deviceRepository) {
        this.categoryRepository = categoryRepository;
        this.deviceRepository = deviceRepository;
    }

    public DeviceCategoryDTO create(DeviceCategory category) {
        return mapToDTO(categoryRepository.save(category));
    }

    public List<DeviceCategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public DeviceCategoryDTO getById(Long id) {
        DeviceCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return mapToDTO(category);
    }

    public DeviceCategoryDTO update(Long id, DeviceCategory input) {
        DeviceCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setCategoryName(input.getCategoryName());
        category.setDescription(input.getDescription()); // ENTITY-ONLY FIELD

        return mapToDTO(categoryRepository.save(category));
    }
 
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    public List<DeviceDTO> getDevicesByCategory(Long categoryId) {
        return deviceRepository.findByCategoryId(categoryId)
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

    private DeviceCategoryDTO mapToDTO(DeviceCategory category) {
        DeviceCategoryDTO dto = new DeviceCategoryDTO();
        dto.setId(category.getId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }
}
