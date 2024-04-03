package com.smartera.service.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.smartera.entities.OrderProduct;
import com.smartera.repository.order.OrderProductRepository;

public class OrderProductServiceTest {

	@InjectMocks
	private OrderProductService orderProductService;

	@Mock
	private OrderProductRepository orderProductRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindAll() {
		List<OrderProduct> orderProducts = new ArrayList<>();
		when(orderProductRepository.findAll()).thenReturn(orderProducts);

		List<OrderProduct> result = orderProductService.findAll();

		assertEquals(orderProducts, result);
	}

	@Test
	public void testFindById() {
		OrderProduct orderProduct = new OrderProduct();
		when(orderProductRepository.findById(1))
				.thenReturn(Optional.of(orderProduct));

		Optional<OrderProduct> result = orderProductService.findById(1);

		assertEquals(Optional.of(orderProduct), result);
	}

	@Test
	public void testSave() {
		OrderProduct orderProduct = new OrderProduct();
		when(orderProductRepository.save(orderProduct))
				.thenReturn(orderProduct);

		OrderProduct result = orderProductService.save(orderProduct);

		assertEquals(orderProduct, result);
	}

	@Test
	public void testUpdate() {
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setId(1);
		orderProduct.setQuantity(10);

		OrderProduct dbOrderProduct = new OrderProduct();
		dbOrderProduct.setId(1);
		dbOrderProduct.setQuantity(5);

		when(orderProductRepository.findById(1))
				.thenReturn(Optional.of(dbOrderProduct));
		when(orderProductRepository.save(dbOrderProduct))
				.thenReturn(dbOrderProduct);

		OrderProduct result = orderProductService.update(orderProduct);

		assertEquals(orderProduct.getId(), result.getId());
		assertEquals(orderProduct.getQuantity(), result.getQuantity());

		verify(orderProductRepository, times(1)).findById(1);
		verify(orderProductRepository, times(1)).save(dbOrderProduct);
	}

	@Test
	public void testDelete() {
		int id = 1;

		orderProductService.delete(id);

		verify(orderProductRepository, times(1)).deleteById(id);
	}

	@Test
	public void testFindById_ExistingId() {
		int id = 1;
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setId(id);
		when(orderProductRepository.findById(id))
				.thenReturn(Optional.of(orderProduct));

		Optional<OrderProduct> result = orderProductService.findById(id);

		assertTrue(result.isPresent());
		assertEquals(id, result.get().getId());
	}

	@Test
	public void testFindById_NonExistingId() {
		int id = 1;
		when(orderProductRepository.findById(id)).thenReturn(Optional.empty());

		Optional<OrderProduct> result = orderProductService.findById(id);

		assertFalse(result.isPresent());
	}

	@Test
	public void testDeleteByOrderId() {
		int id = 1;

		orderProductService.deleteByOrderId(id);

		verify(orderProductRepository, times(1)).deleteByOrderId(id);
	}
}
