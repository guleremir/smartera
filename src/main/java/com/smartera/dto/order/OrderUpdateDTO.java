package com.smartera.dto.order;

import java.util.List;

import com.smartera.dto.orderProduct.OrderProductUpdateDTO;

import lombok.Data;

@Data
public class OrderUpdateDTO {
	private int id;
	private int userId;
	private List<OrderProductUpdateDTO> orderProducts;
}
