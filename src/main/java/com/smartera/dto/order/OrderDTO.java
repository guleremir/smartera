package com.smartera.dto.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smartera.dto.orderProduct.OrderProductDTO;
import com.smartera.dto.user.UserDTO;

import lombok.Data;

@Data
public class OrderDTO {
	private UserDTO user;
	@JsonManagedReference
	private List<OrderProductDTO> orderProducts;
}
