package com.smartera.controller.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartera.dto.ErrorResponseDTO;
import com.smartera.dto.SuccessResponseDTO;
import com.smartera.dto.order.OrderDTO;
import com.smartera.dto.order.OrderDeleteDTO;
import com.smartera.dto.order.OrderSaveDTO;
import com.smartera.dto.order.OrderGetAllDTO;
import com.smartera.entities.Order;
import com.smartera.entities.OrderProduct;
import com.smartera.entities.Product;
import com.smartera.entities.User;
import com.smartera.service.order.OrderProductService;
import com.smartera.service.order.OrderService;
import com.smartera.service.user.UserService;

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

	@Autowired
	@Qualifier("requestMapper")
	private ModelMapper requestMapper;

	@Autowired
	@Qualifier("responseMapper")
	private ModelMapper responseMapper;

	@GetMapping("/getAll")
	public ResponseEntity<List<OrderGetAllDTO>> getAll() {
		List<Order> allOrder = orderDetailService.findAll();
		List<OrderGetAllDTO> allOrderDTO = new ArrayList<>();
		allOrder.forEach(order -> {
			allOrderDTO.add(responseMapper.map(order, OrderGetAllDTO.class));
		});
		return ResponseEntity.ok(allOrderDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable int id) {
		Optional<Order> optionalOrderDetail = orderDetailService.findById(id);
		if (optionalOrderDetail.isPresent()) {
			OrderDTO dto = responseMapper.map(optionalOrderDetail,
					OrderDTO.class);
			return ResponseEntity.ok(dto);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Order not found with id: " + id);
		}
	}

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody OrderSaveDTO dto) {
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
			return ResponseEntity.ok(new SuccessResponseDTO("Order created!"));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponseDTO("Error user has not role."));

		}

	}

	@Transactional
	@DeleteMapping("/delete")
	public SuccessResponseDTO delete(@RequestBody OrderDeleteDTO dto) {
		orderProductService.deleteByOrderId(dto.getId());
		orderDetailService.delete(dto.getId());
		return new SuccessResponseDTO("Order deleted.");
	}

}
