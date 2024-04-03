package com.smartera.controller.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.smartera.dto.SuccessResponseDTO;
import com.smartera.dto.order.OrderDTO;
import com.smartera.dto.order.OrderDeleteDTO;
import com.smartera.dto.order.OrderGetAllDTO;
import com.smartera.dto.order.OrderSaveDTO;
import com.smartera.dto.orderProduct.OrderProductSaveDTO;
import com.smartera.dto.user.UserDTO;
import com.smartera.entities.Order;
import com.smartera.entities.OrderProduct;
import com.smartera.entities.User;
import com.smartera.service.order.OrderProductService;
import com.smartera.service.order.OrderService;
import com.smartera.service.user.UserService;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

	@InjectMocks
	private OrderController orderController;

	@Mock
	private OrderService orderDetailService;

	@Mock
	private OrderProductService orderProductService;

	@Mock
	private UserService userService;

	@Mock
	@Qualifier("requestMapper")
	private ModelMapper requestMapper;

	@Mock
	@Qualifier("responseMapper")
	private ModelMapper responseMapper;

	@Test
	public void testGetAll() {
		User user = new User();
		user.setId(1);

		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setId(1);

		Order order = new Order();
		order.setId(1);
		order.setUser(user);
		order.setOrderProducts(Collections.singletonList(orderProduct));

		OrderGetAllDTO orderGetAllDTO = new OrderGetAllDTO();
		orderGetAllDTO.setUser(new UserDTO());
		orderGetAllDTO.setOrderProducts(new ArrayList<>());

		when(orderDetailService.findAll())
				.thenReturn(Collections.singletonList(order));
		when(responseMapper.map(any(), Mockito.eq(OrderGetAllDTO.class)))
				.thenReturn(orderGetAllDTO);

		ResponseEntity<List<OrderGetAllDTO>> responseEntity = orderController
				.getAll();

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(1,
				Objects.requireNonNull(responseEntity.getBody()).size());
	}

	@Test
	public void testGetById_WhenOrderExists() {
		int orderId = 1;
		Order order = new Order();
		order.setId(orderId);
		User user = new User();
		user.setId(1);
		order.setUser(user);

		when(orderDetailService.findById(orderId))
				.thenReturn(Optional.of(order));
		when(responseMapper.map(any(), Mockito.eq(OrderDTO.class)))
				.thenReturn(new OrderDTO());

		ResponseEntity<?> responseEntity = orderController.getById(orderId);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testGetById_WhenOrderNotExists() {
		int orderId = 1;

		when(orderDetailService.findById(orderId)).thenReturn(Optional.empty());

		ResponseEntity<?> responseEntity = orderController.getById(orderId);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void testSave_WhenUserHasPermission() {
		OrderSaveDTO dto = new OrderSaveDTO();
		dto.setUserId(1);
		dto.setOrderProducts(
				Collections.singletonList(new OrderProductSaveDTO()));

		User user = new User();
		user.setId(1);
		user.setPerm(1);

		when(userService.findById(1)).thenReturn(Optional.of(user));
		when(orderDetailService.save(any(Order.class))).thenReturn(new Order());
		when(orderProductService.save(any(OrderProduct.class)))
				.thenReturn(new OrderProduct());

		ResponseEntity<?> responseEntity = orderController.save(dto);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testSave_WhenUserDoesNotHavePermission() {
		OrderSaveDTO dto = new OrderSaveDTO();
		dto.setUserId(1);

		User user = new User();
		user.setId(1);
		user.setPerm(0);

		when(userService.findById(1)).thenReturn(Optional.of(user));

		ResponseEntity<?> responseEntity = orderController.save(dto);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
	}

	@Test
	public void testDelete() {
		int orderId = 1;
		OrderDeleteDTO dto = new OrderDeleteDTO();
		dto.setId(orderId);

		SuccessResponseDTO responseDTO = orderController.delete(dto);

		assertNotNull(responseDTO);
		assertEquals("Order deleted.", responseDTO.getMessage());

		verify(orderProductService, times(1)).deleteByOrderId(orderId);
		verify(orderDetailService, times(1)).delete(orderId);
	}

}