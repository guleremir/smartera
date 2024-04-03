package com.smartera.service.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.smartera.entities.Order;
import com.smartera.repository.order.OrderRepository;

public class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository orderRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindAll() {
		List<Order> orders = new ArrayList<>();
		when(orderRepository.findAll()).thenReturn(orders);

		List<Order> result = orderService.findAll();

		assertEquals(orders, result);
	}

	@Test
	public void testFindById() {
		int id = 1;
		Order order = new Order();
		order.setId(id);
		when(orderRepository.findById(id)).thenReturn(Optional.of(order));

		Optional<Order> result = orderService.findById(id);

		assertTrue(result.isPresent());
		assertEquals(id, result.get().getId());
	}

	@Test
	public void testSave() {
		Order order = new Order();
		when(orderRepository.save(order)).thenReturn(order);

		Order result = orderService.save(order);

		assertEquals(order, result);
	}

	@Test
	public void testDelete() {
		int id = 1;

		orderService.delete(id);

		verify(orderRepository, times(1)).deleteById(id);
	}
}