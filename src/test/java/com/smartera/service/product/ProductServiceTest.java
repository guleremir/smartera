package com.smartera.service.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.smartera.entities.Product;
import com.smartera.repository.product.ProductRepository;

public class ProductServiceTest {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindAll() {
		List<Product> products = new ArrayList<>();
		when(productRepository.findAll()).thenReturn(products);

		List<Product> result = productService.findAll();

		assertEquals(products, result);
	}

	@Test
	public void testFindById() {
		int id = 1;
		Product product = new Product();
		product.setId(id);
		when(productRepository.findById(id)).thenReturn(Optional.of(product));

		Optional<Product> result = productService.findById(id);

		assertTrue(result.isPresent());
		assertEquals(id, result.get().getId());
	}

	@Test
	public void testSave() {
		Product product = new Product();
		when(productRepository.save(product)).thenReturn(product);

		Product result = productService.save(product);

		assertEquals(product, result);
	}

	@Test
	public void testUpdate() {
		Product product = new Product();
		product.setId(1);
		product.setName("Test Product");

		Product dbProduct = new Product();
		dbProduct.setId(1);
		dbProduct.setName("Old Product");

		when(productRepository.findById(1)).thenReturn(Optional.of(dbProduct));
		when(productRepository.save(dbProduct)).thenReturn(dbProduct);

		Product result = productService.update(product);

		assertEquals(product.getName(), result.getName());
	}

	@Test
	public void testDelete() {
		int id = 1;

		productService.delete(id);

		verify(productRepository, times(1)).deleteById(id);
	}
}
