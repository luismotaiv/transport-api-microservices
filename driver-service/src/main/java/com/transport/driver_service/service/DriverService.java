package com.transport.driver_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.transport.driver_service.model.Driver;
import com.transport.driver_service.repository.DriverRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository repository;

    public Driver create(Driver driver) {
        return repository.save(driver);
    }

    public List<Driver> getActiveDrivers() {
        return repository.findByActiveTrue();
    }
}