package com.smartera.service.order;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartera.entities.Order;
import com.smartera.repository.order.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

	// GetAll
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	// GetById
	public Optional<Order> findById(int id) {
		return orderRepository.findById(id);
	}

	// Save order
	public Order save(Order orderDetail) {
		return orderRepository.save(orderDetail);
	}

	// Delete order
	public void delete(int id) {
		orderRepository.deleteById(id);
	}

}
