package com.smartera.dto.orderProduct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smartera.dto.order.OrderDTO;
import com.smartera.dto.product.ProductDTO;

import lombok.Data;

@Data
public class OrderProductDTO {
	@JsonBackReference
	private OrderDTO order;
	private ProductDTO product;
	private int quantity;
}
