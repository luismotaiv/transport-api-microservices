package com.transport.driver_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String licenseNumber;

    private boolean active;
}