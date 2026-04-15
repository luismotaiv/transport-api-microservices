package com.transport.driver_service.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverResponseDTO {

    private UUID id;
    private String name;
    private String licenseNumber;
    private boolean active;
}