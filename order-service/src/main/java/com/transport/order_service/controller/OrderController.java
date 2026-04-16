package com.transport.order_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transport.order_service.dto.AssignDriverRequestDTO;
import com.transport.order_service.dto.OrderRequestDTO;
import com.transport.order_service.dto.OrderResponseDTO;
import com.transport.order_service.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Gestión de órdenes de transporte")
public class OrderController {

    private final OrderService service;

    @Operation(summary = "Crear una nueva orden")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(
            @Valid @RequestBody OrderRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(dto));
    }

    @Operation(summary = "Asignar driver y archivos a una orden")
    @PatchMapping("/{id}/driver")
    public ResponseEntity<OrderResponseDTO> assignDriver(
            @Parameter(description = "ID de la orden")
            @PathVariable UUID id,

            @RequestBody AssignDriverRequestDTO dto,

            @Parameter(description = "Token JWT")
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(service.assignDriver(id, dto, token));
    }

    @Operation(summary = "Filtrar órdenes")
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> filter(

            @Parameter(description = "Estado de la orden")
            @RequestParam(required = false) String status,

            @Parameter(description = "Origen")
            @RequestParam(required = false) String origin,

            @Parameter(description = "Destino")
            @RequestParam(required = false) String destination) {

        return ResponseEntity.ok(service.filter(status, origin, destination));
    }
}