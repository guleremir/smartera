package com.smartera.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartera.dto.orderProduct.OrderProductDeleteDTO;
import com.smartera.dto.orderProduct.OrderProductSaveDTO;
import com.smartera.dto.orderProduct.OrderProductUpdateDTO;
import com.smartera.entities.Order;
import com.smartera.entities.OrderProduct;
import com.smartera.entities.Product;
import com.smartera.service.OrderProductService;
import com.smartera.service.OrderService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("order-product")
public class OrderProductController {

	@Autowired
	private OrderProductService orderProductService;
	@Autowired
	private OrderService orderService;

	@PostMapping("/save")
	public OrderProduct save(@RequestBody OrderProductSaveDTO dto) {
		OrderProduct newOrderProduct = new OrderProduct();
		Order order = new Order();
		order.setId(dto.getOrderId());
		Product product = new Product();
		product.setId(dto.getProductId());
		newOrderProduct.setOrder(order);
		newOrderProduct.setProduct(product);
		newOrderProduct.setQuantity(dto.getQuantity());
		return orderProductService.save(newOrderProduct);
	}

	@PostMapping("/update")
	public OrderProduct update(@RequestBody OrderProductUpdateDTO dto) {
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
		}
		return dbOrderProduct.get();
	}

	@Transactional
	@DeleteMapping("/delete")
	public void delete(@RequestBody OrderProductDeleteDTO dto) {
		orderProductService.delete(dto.getId());
		List<OrderProduct> listOrderProduct = orderProductService
				.findByOrderId(dto.getOrderId());
		if (listOrderProduct.isEmpty()) {
			orderService.delete(dto.getOrderId());
		}

	}
}
