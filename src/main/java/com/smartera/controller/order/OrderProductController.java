package com.smartera.controller.order;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartera.dto.ErrorResponseDTO;
import com.smartera.dto.SuccessResponseDTO;
import com.smartera.dto.orderProduct.OrderProductDeleteDTO;
import com.smartera.dto.orderProduct.OrderProductSaveDTO;
import com.smartera.dto.orderProduct.OrderProductUpdateDTO;
import com.smartera.entities.Order;
import com.smartera.entities.OrderProduct;
import com.smartera.entities.Product;
import com.smartera.service.order.OrderProductService;
import com.smartera.service.order.OrderService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("order-product")
public class OrderProductController {

	@Autowired
	private OrderProductService orderProductService;
	@Autowired
	private OrderService orderService;

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody OrderProductSaveDTO dto) {
		try {
			OrderProduct newOrderProduct = new OrderProduct();
			Order order = new Order();
			order.setId(dto.getOrderId());
			Product product = new Product();
			product.setId(dto.getProductId());
			newOrderProduct.setOrder(order);
			newOrderProduct.setProduct(product);
			newOrderProduct.setQuantity(dto.getQuantity());
			orderProductService.save(newOrderProduct);
			return ResponseEntity
					.ok(new SuccessResponseDTO("Order product created"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ErrorResponseDTO("Failed to create order product"));
		}

	}

	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody OrderProductUpdateDTO dto) {
		Optional<OrderProduct> dbOrderProduct = orderProductService
				.findById(dto.getId());
		if (dbOrderProduct.isPresent()) {
			Order order = new Order();
			order.setId(dto.getOrderId());
			Product product = new Product();
			product.setId(dto.getProductId());
			dbOrderProduct.get().setOrder(order);
			dbOrderProduct.get().setProduct(product);
			dbOrderProduct.get().setQuantity(dto.getQuantity());
			orderProductService.update(dbOrderProduct.get());
			return ResponseEntity
					.ok(new SuccessResponseDTO("Order product updated"));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ErrorResponseDTO("Failed to update order product"));
		}
	}

	@Transactional
	@DeleteMapping("/delete")
	public SuccessResponseDTO delete(@RequestBody OrderProductDeleteDTO dto) {
		orderProductService.delete(dto.getId());
		List<OrderProduct> listOrderProduct = orderProductService
				.findByOrderId(dto.getOrderId());
		if (listOrderProduct.isEmpty()) {
			orderService.delete(dto.getOrderId());
		}
		return new SuccessResponseDTO("Order product deleted.");

	}
}
