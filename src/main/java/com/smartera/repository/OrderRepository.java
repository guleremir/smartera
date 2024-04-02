package com.smartera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartera.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
