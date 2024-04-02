package com.smartera.service.order;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartera.entities.OrderProduct;
import com.smartera.repository.order.OrderProductRepository;

@Service
public class OrderProductService {

	@Autowired
	private OrderProductRepository orderProductRepository;

	// GetAll
	public List<OrderProduct> findAll() {
		return orderProductRepository.findAll();
	}

	// GetById
	public Optional<OrderProduct> findById(int id) {
		return orderProductRepository.findById(id);
	}

	public List<OrderProduct> findByOrderId(int id) {
		return orderProductRepository.findByOrderId(id);
	}

	// Save order product
	public OrderProduct save(OrderProduct orderProduct) {
		return orderProductRepository.save(orderProduct);
	}

	// Update order product
	public OrderProduct update(OrderProduct orderProduct) {
		OrderProduct dbOrderProduct = orderProductRepository
				.findById(orderProduct.getId()).get();
		dbOrderProduct.setQuantity(orderProduct.getQuantity());
		return orderProductRepository.save(dbOrderProduct);

	}

	// Delete order product
	public void delete(int id) {
		orderProductRepository.deleteById(id);
	}

	public void deleteByOrderId(int id) {
		orderProductRepository.deleteByOrderId(id);
	}

}
