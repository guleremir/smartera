package com.smartera.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartera.entities.OrderProduct;

public interface OrderProductRepository
		extends JpaRepository<OrderProduct, Integer> {

	List<OrderProduct> findByOrderId(int id);

	void deleteByOrderId(int id);
}
