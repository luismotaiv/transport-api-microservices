package com.transport.driver_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.transport.driver_service.dto.DriverRequestDTO;
import com.transport.driver_service.dto.DriverResponseDTO;
import com.transport.driver_service.exception.BadRequestException;
import com.transport.driver_service.exception.ResourceNotFoundException;
import com.transport.driver_service.model.Driver;
import com.transport.driver_service.repository.DriverRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository repository;

    public DriverResponseDTO create(DriverRequestDTO dto) {

        if (repository.existsByLicenseNumber(dto.getLicenseNumber())) {
            throw new BadRequestException("License already exists");
        }

        Driver driver = new Driver();
        driver.setName(dto.getName());
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setActive(dto.isActive());

        Driver saved = repository.save(driver);

        return mapToDTO(saved);
    }

    public List<DriverResponseDTO> getActiveDrivers() {

        return repository.findByActiveTrue()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public DriverResponseDTO getById(UUID id) {
        Driver driver = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        return mapToDTO(driver);
    }

    private DriverResponseDTO mapToDTO(Driver driver) {
        return new DriverResponseDTO(
                driver.getId(),
                driver.getName(),
                driver.getLicenseNumber(),
                driver.isActive()
        );
    }
}