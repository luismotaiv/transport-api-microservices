package com.transport.driver_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transport.driver_service.dto.DriverRequestDTO;
import com.transport.driver_service.dto.DriverResponseDTO;
import com.transport.driver_service.service.DriverService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService service;

    @PostMapping
    public ResponseEntity<DriverResponseDTO> create(@Valid @RequestBody DriverRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping("/active")
    public List<DriverResponseDTO> getActiveDrivers() {
        return service.getActiveDrivers();
    }

    @GetMapping("/{id}")
    public DriverResponseDTO getById(@PathVariable UUID id) {
        return service.getById(id);
    }
}