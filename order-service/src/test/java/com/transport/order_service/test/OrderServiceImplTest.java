package com.transport.order_service.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.transport.order_service.client.DriverClient;
import com.transport.order_service.dto.AssignDriverRequestDTO;
import com.transport.order_service.dto.OrderResponseDTO;
import com.transport.order_service.exception.BadRequestException;
import com.transport.order_service.model.Order;
import com.transport.order_service.repository.OrderRepository;
import com.transport.order_service.service.OrderService;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DriverClient driverClient;
    @InjectMocks
    private OrderService orderService;
    
    @Test
    void shouldAssignDriverSuccessfully() {

        UUID orderId = UUID.randomUUID();
        UUID driverId = UUID.randomUUID();

        Order order = new Order();
        order.setStatus("CREATED");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(driverClient.isDriverActive(driverId, "token")).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(order);

        AssignDriverRequestDTO dto = new AssignDriverRequestDTO();
        dto.setDriverId(driverId);
        dto.setPdfUrl("pdf-url");
        dto.setImageUrl("img-url");

        OrderResponseDTO response = orderService.assignDriver(orderId, dto, "token");

        assertNotNull(response);
        verify(orderRepository).save(order);
    }
    
    @Test
    void shouldThrowExceptionWhenDriverIsInactive() {

        UUID orderId = UUID.randomUUID();
        UUID driverId = UUID.randomUUID();

        Order order = new Order();
        order.setStatus("CREATED");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(driverClient.isDriverActive(driverId, "token")).thenReturn(false);

        AssignDriverRequestDTO dto = new AssignDriverRequestDTO();
        dto.setDriverId(driverId);

        assertThrows(BadRequestException.class, () ->
            orderService.assignDriver(orderId, dto, "token")
        );
    }
}