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

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PatchMapping("/{id}/driver")
    public OrderResponseDTO assignDriver(
            @PathVariable UUID id,
            @RequestBody AssignDriverRequestDTO dto,
            @RequestHeader("Authorization") String token) {

        return service.assignDriver(id, dto, token);
    }

    @GetMapping
    public List<OrderResponseDTO> filter(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination) {

        return service.filter(status, origin, destination);
    }
}