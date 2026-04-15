package com.transport.driver_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transport.driver_service.model.Driver;
import com.transport.driver_service.service.DriverService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService service;

    @PostMapping
    public Driver create(@RequestBody Driver driver) {
        return service.create(driver);
    }

    @GetMapping("/active")
    public List<Driver> getActiveDrivers() {
        return service.getActiveDrivers();
    }
}