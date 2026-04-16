package com.transport.order_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.transport.order_service.client.DriverClient;
import com.transport.order_service.dto.AssignDriverRequestDTO;
import com.transport.order_service.dto.OrderRequestDTO;
import com.transport.order_service.dto.OrderResponseDTO;
import com.transport.order_service.exception.BadRequestException;
import com.transport.order_service.exception.ResourceNotFoundException;
import com.transport.order_service.model.Order;
import com.transport.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final DriverClient driverClient;

    public OrderResponseDTO create(OrderRequestDTO dto) {

        Order order = new Order();
        order.setOrigin(dto.getOrigin());
        order.setDestination(dto.getDestination());
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        return mapToDTO(repository.save(order));
    }

    public OrderResponseDTO assignDriver(UUID orderId,
                                         AssignDriverRequestDTO dto,
                                         String token) {

        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getStatus().equals("CREATED")) {
            throw new BadRequestException("Order must be in CREATED status");
        }

        boolean driverActive = driverClient.isDriverActive(dto.getDriverId(), token);

        if (!driverActive) {
            throw new BadRequestException("Driver not available");
        }

        order.setDriverId(dto.getDriverId());
        order.setPdfUrl(dto.getPdfUrl());
        order.setImageUrl(dto.getImageUrl());
        order.setUpdatedAt(LocalDateTime.now());

        return mapToDTO(repository.save(order));
    }

    public List<OrderResponseDTO> filter(String status, String origin, String destination) {

        List<Order> orders;

        if (status != null) {
            orders = repository.findByStatus(status);
        } else if (origin != null) {
            orders = repository.findByOrigin(origin);
        } else if (destination != null) {
            orders = repository.findByDestination(destination);
        } else {
            orders = repository.findAll();
        }

        return orders.stream().map(this::mapToDTO).toList();
    }

    private OrderResponseDTO mapToDTO(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getStatus(),
                order.getOrigin(),
                order.getDestination(),
                order.getDriverId(),
                order.getPdfUrl(),
                order.getImageUrl(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}