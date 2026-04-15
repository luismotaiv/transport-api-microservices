package com.transport.order_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private UUID id;
    private String status;
    private String origin;
    private String destination;
    private UUID driverId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}