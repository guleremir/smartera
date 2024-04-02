package com.smartera.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartera.dto.order.OrderDeleteDTO;
import com.smartera.dto.order.OrderSaveDTO;
import com.smartera.entities.Order;
import com.smartera.entities.OrderProduct;
import com.smartera.entities.Product;
import com.smartera.entities.User;
import com.smartera.service.OrderProductService;
import com.smartera.service.OrderService;
import com.smartera.service.UserService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("order")
public class OrderController {

	@Autowired
	private OrderService orderDetailService;

	@Autowired
	private OrderProductService orderProductService;

	@Autowired
	private UserService userService;

	@GetMapping("/getAll")
	public ResponseEntity<List<Order>> getAll() {
		List<Order> allOrderDetail = orderDetailService.findAll();
		return ResponseEntity.ok(allOrderDetail);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable int id) {
		Optional<Order> optionalOrderDetail = orderDetailService.findById(id);
		if (optionalOrderDetail.isPresent()) {
			return ResponseEntity.ok(optionalOrderDetail.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Order not found with id: " + id);
		}
	}

	@PostMapping("/save")
	public Order save(@RequestBody OrderSaveDTO dto) {
		Optional<User> dbUser = userService.findById(dto.getUserId());

		if (dbUser.isPresent() && dbUser.get().getPerm() == 1) {
			Order newOrder = new Order();

			newOrder.setUser(dbUser.get());
			Order dbOrder = orderDetailService.save(newOrder);
			List<OrderProduct> listOrderProduct = new ArrayList<>();
			dto.getOrderProducts().forEach(op -> {
				OrderProduct newOrderProduct = new OrderProduct();
				newOrderProduct.setOrder(dbOrder);
				Product product = new Product();
				product.setId(op.getProductId());
				newOrderProduct.setProduct(product);
				newOrderProduct.setQuantity(op.getQuantity());
				orderProductService.save(newOrderProduct);
				listOrderProduct.add(orderProductService.save(newOrderProduct));

			});
			dbOrder.setOrderProducts(listOrderProduct);
			return dbOrder;
		} else {
			return null;
		}

	}

//	@PutMapping("/update")
//	public Order update(@RequestBody OrderUpdateDTO dto) {
//		Optional<Order> dbOrder = orderDetailService.findById(dto.getId());
//		if (dbOrder.isPresent()) {
//			List<OrderProduct> listOrderProduct = new ArrayList<>();
//			dto.getOrderProducts().forEach(op -> {
//				OrderProduct newOrderProduct = new OrderProduct();
//				newOrderProduct.setOrder(dbOrder.get());
//				Product product = new Product();
//				product.setId(op.getProductId());
//				newOrderProduct.setProduct(product);
//				newOrderProduct.setQuantity(op.getQuantity());
//				orderProductService.save(newOrderProduct);
//				listOrderProduct.add(orderProductService.save(newOrderProduct));
//			});
//
//			dbOrder.get().setOrderProducts(listOrderProduct);
//		}
//		return dbOrder.get();
//	}

	@Transactional
	@DeleteMapping("/delete")
	public void delete(@RequestBody OrderDeleteDTO dto) {
		orderProductService.deleteByOrderId(dto.getId());
		orderDetailService.delete(dto.getId());
	}

}
