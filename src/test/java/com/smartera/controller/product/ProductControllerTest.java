package com.smartera.controller.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.smartera.dto.ErrorResponseDTO;
import com.smartera.dto.SuccessResponseDTO;
import com.smartera.dto.product.ProductDTO;
import com.smartera.dto.product.ProductDeleteDTO;
import com.smartera.dto.product.ProductEditDTO;
import com.smartera.dto.product.ProductGetAllDTO;
import com.smartera.dto.product.ProductSaveDTO;
import com.smartera.entities.Product;
import com.smartera.service.product.ProductService;

public class ProductControllerTest {

	@InjectMocks
	private ProductController productController;

	@Mock
	private ProductService productService;

	@Mock
	private ModelMapper requestMapper;

	@Mock
	private ModelMapper responseMapper;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAll() {
		List<Product> products = new ArrayList<>();
		List<ProductGetAllDTO> productDTOs = new ArrayList<>();
		when(productService.findAll()).thenReturn(products);
		when(responseMapper.map(any(), any()))
				.thenReturn(new ProductGetAllDTO());

		ResponseEntity<List<ProductGetAllDTO>> responseEntity = productController
				.getAll();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(productDTOs, responseEntity.getBody());
	}

	@Test
	public void testGetById_ProductFound() {
		int id = 1;
		Product product = new Product();
		product.setId(id);
		when(productService.findById(id)).thenReturn(Optional.of(product));
		when(responseMapper.map(any(), any())).thenReturn(new ProductDTO());

		ResponseEntity<?> responseEntity = productController.getById(id);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testGetById_ProductNotFound() {
		int id = 1;
		when(productService.findById(id)).thenReturn(Optional.empty());

		ResponseEntity<?> responseEntity = productController.getById(id);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void testSave() {
		ProductSaveDTO dto = new ProductSaveDTO();
		Product product = new Product();
		when(requestMapper.map(dto, Product.class)).thenReturn(product);

		SuccessResponseDTO responseDTO = productController.save(dto);

		assertEquals("Product saved!", responseDTO.getMessage());
	}

	@Test
	public void testUpdate() {
		ProductEditDTO dto = new ProductEditDTO();
		Product product = new Product();
		when(requestMapper.map(dto, Product.class)).thenReturn(product);

		ResponseEntity<?> responseEntity = productController.update(dto);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testUpdate_Exception() {
		ProductEditDTO dto = new ProductEditDTO();
		Product product = new Product();
		when(requestMapper.map(dto, Product.class)).thenReturn(product);
		when(productService.update(product))
				.thenThrow(new RuntimeException("Update failed"));

		ResponseEntity<?> responseEntity = productController.update(dto);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
				responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody() instanceof ErrorResponseDTO);
		ErrorResponseDTO errorResponseDTO = (ErrorResponseDTO) responseEntity
				.getBody();
		assertEquals("Failed to update product", errorResponseDTO.getMessage());
	}

	@Test
	public void testDelete() {
		int id = 1;
		ProductDeleteDTO dto = new ProductDeleteDTO();
		dto.setId(id);

		SuccessResponseDTO responseDTO = productController.delete(dto);

		assertEquals("Product deleted!", responseDTO.getMessage());
	}
}
