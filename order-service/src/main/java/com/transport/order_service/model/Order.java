package com.transport.order_service.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    private String origin;
    private String destination;

    private String status;

    private UUID driverId; // SOLO ID (microservicios)
    
    private String pdfUrl;
    private String imageUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}