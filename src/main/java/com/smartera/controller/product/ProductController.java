package com.smartera.controller.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.smartera.dto.ErrorResponseDTO;
import com.smartera.dto.SuccessResponseDTO;
import com.smartera.dto.product.ProductDTO;
import com.smartera.dto.product.ProductDeleteDTO;
import com.smartera.dto.product.ProductEditDTO;
import com.smartera.dto.product.ProductGetAllDTO;
import com.smartera.dto.product.ProductSaveDTO;
import com.smartera.entities.Product;
import com.smartera.service.product.ProductService;

@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	@Qualifier("requestMapper")
	private ModelMapper requestMapper;

	@Autowired
	@Qualifier("responseMapper")
	private ModelMapper responseMapper;

	@GetMapping("/getAll")
	public ResponseEntity<List<ProductGetAllDTO>> getAll() {
		List<Product> allProduct = productService.findAll();
		List<ProductGetAllDTO> allProductDTO = new ArrayList<>();
		allProduct.forEach(product -> {
			allProductDTO
					.add(responseMapper.map(product, ProductGetAllDTO.class));
		});
		return ResponseEntity.ok(allProductDTO);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable int id) {
		Optional<Product> optionalProduct = productService.findById(id);
		if (optionalProduct.isPresent()) {
			ProductDTO dto = responseMapper.map(optionalProduct,
					ProductDTO.class);
			return ResponseEntity.ok(dto);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Product not found with id: " + id);
		}
	}

	@PostMapping("/save")
	public SuccessResponseDTO save(@RequestBody ProductSaveDTO dto) {
		Product product = requestMapper.map(dto, Product.class);
		productService.save(product);
		return new SuccessResponseDTO("Product saved!");

	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody ProductEditDTO dto) {
		try {
			Product product = requestMapper.map(dto, Product.class);
			productService.update(product);
			return ResponseEntity
					.ok(new SuccessResponseDTO("Product updated!"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponseDTO("Failed to update product"));
		}
	}

	@DeleteMapping("/delete")
	public SuccessResponseDTO delete(@RequestBody ProductDeleteDTO dto) {
		productService.delete(dto.getId());
		return new SuccessResponseDTO("Product deleted!");
	}

}
