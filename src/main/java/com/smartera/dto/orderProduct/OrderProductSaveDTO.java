package com.smartera.dto.orderProduct;

import lombok.Data;

@Data
public class OrderProductSaveDTO {
	private int orderId;
	private int quantity;
	private int productId;
}
