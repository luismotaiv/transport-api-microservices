package com.transport.order_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.order_service.model.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

	List<Order> findByStatus(String status);
	
	List<Order> findByOrigin(String origin);
	
	List<Order> findByDestination(String destination);
	
}