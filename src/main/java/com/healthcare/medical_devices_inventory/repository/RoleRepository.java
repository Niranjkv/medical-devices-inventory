package com.healthcare.medical_devices_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.medical_devices_inventory.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // Custom queries can be added if needed
}
