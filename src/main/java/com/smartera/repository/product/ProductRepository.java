package com.smartera.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartera.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
