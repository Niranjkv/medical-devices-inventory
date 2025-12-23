package com.healthcare.medical_devices_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.medical_devices_inventory.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Additional custom queries can be added here if needed
}

