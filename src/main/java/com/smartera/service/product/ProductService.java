package com.smartera.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartera.entities.Product;
import com.smartera.repository.product.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	// GetAll
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	// GetById
	public Optional<Product> findById(int id) {
		return productRepository.findById(id);
	}

	// Save product
	public Product save(Product product) {
		return productRepository.save(product);
	}

	// Update product
	public Product update(Product product) {
		Product dbProduct = productRepository.findById(product.getId()).get();
		dbProduct.setName(product.getName());
		return productRepository.save(dbProduct);

	}

	// Delete product
	public void delete(int id) {
		productRepository.deleteById(id);
	}
}
