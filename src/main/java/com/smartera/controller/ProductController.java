package com.smartera.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartera.entities.Product;
import com.smartera.service.ProductService;

@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/getAll")
	public ResponseEntity<List<Product>> getAll() {
		List<Product> allProduct = productService.findAll();
		return ResponseEntity.ok(allProduct);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable int id) {
		Optional<Product> optionalProduct = productService.findById(id);
		if (optionalProduct.isPresent()) {
			return ResponseEntity.ok(optionalProduct.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Product not found with id: " + id);
		}
	}

	@PostMapping("/save")
	public Product save(@RequestBody Product product) {
		return productService.save(product);
	}

	@PutMapping("/update")
	public Product update(@RequestBody Product product) {
		return productService.update(product);
	}

	@DeleteMapping("/delete")
	public void delete(@RequestBody Product product) {
		productService.delete(product.getId());
	}

}
