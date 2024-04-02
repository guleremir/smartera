package com.smartera.dto.orderProduct;

import lombok.Data;

@Data
public class OrderProductUpdateDTO {
	private int id;
	private int orderId;
	private int productId;
	private int quantity;
}
