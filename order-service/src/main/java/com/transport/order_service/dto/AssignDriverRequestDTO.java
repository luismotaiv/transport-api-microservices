package com.transport.order_service.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignDriverRequestDTO {

    @NotNull
    private UUID driverId;
    
    private String pdfUrl;
    private String imageUrl;
}