package com.smartera.dto.order;

import java.util.List;

import com.smartera.dto.orderProduct.OrderProductSaveDTO;

import lombok.Data;

@Data
public class OrderSaveDTO {
	private int userId;
	private List<OrderProductSaveDTO> orderProducts;
}
