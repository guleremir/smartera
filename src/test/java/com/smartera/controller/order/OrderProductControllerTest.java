package com.smartera.controller.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.smartera.dto.SuccessResponseDTO;
import com.smartera.dto.orderProduct.OrderProductDeleteDTO;
import com.smartera.dto.orderProduct.OrderProductSaveDTO;
import com.smartera.dto.orderProduct.OrderProductUpdateDTO;
import com.smartera.entities.OrderProduct;
import com.smartera.service.order.OrderProductService;
import com.smartera.service.order.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderProductControllerTest {

	@InjectMocks
	private OrderProductController orderProductController;

	@Mock
	private OrderProductService orderProductService;

	@Mock
	private OrderService orderService;

	@Test
	public void testSave() {
		OrderProductSaveDTO dto = new OrderProductSaveDTO();
		dto.setOrderId(1);
		dto.setProductId(1);
		dto.setQuantity(1);

		when(orderProductService.save(any(OrderProduct.class)))
				.thenReturn(new OrderProduct());

		ResponseEntity<?> responseEntity = orderProductController.save(dto);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testUpdate_WhenOrderProductExists() {
		OrderProductUpdateDTO dto = new OrderProductUpdateDTO();
		dto.setId(1);
		dto.setOrderId(1);
		dto.setProductId(1);
		dto.setQuantity(1);

		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setId(1);

		when(orderProductService.findById(1))
				.thenReturn(Optional.of(orderProduct));
		when(orderProductService.update(any(OrderProduct.class)))
				.thenReturn(new OrderProduct());

		ResponseEntity<?> responseEntity = orderProductController.update(dto);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testUpdate_WhenOrderProductNotExists() {
		OrderProductUpdateDTO dto = new OrderProductUpdateDTO();
		dto.setId(1);

		when(orderProductService.findById(1)).thenReturn(Optional.empty());

		ResponseEntity<?> responseEntity = orderProductController.update(dto);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
				responseEntity.getStatusCode());
	}

	@Test
	public void testDelete() {
		OrderProductDeleteDTO dto = new OrderProductDeleteDTO();
		dto.setId(1);
		dto.setOrderId(1);

		List<OrderProduct> orderProducts = new ArrayList<>();
		orderProducts.add(new OrderProduct());

		when(orderProductService.findByOrderId(1)).thenReturn(orderProducts);

		SuccessResponseDTO responseDTO = orderProductController.delete(dto);

		assertNotNull(responseDTO);
		assertEquals("Order product deleted.", responseDTO.getMessage());
		verify(orderProductService, times(1)).delete(1);
	}
}