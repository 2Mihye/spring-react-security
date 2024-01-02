package com.kh.springChap1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kh.springChap1.model.Product;
import com.kh.springChap1.repository.ProductRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:3000", allowCredentials="true")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/item")
	public List <Product> getAllProduct() {
		return productRepository.findAll();
	}
	
	@PostMapping("/add")
	public ResponseEntity<Product> addProduct(@RequestBody Product product){
		Product saveProduct = productRepository.save(product);
		return ResponseEntity.ok(saveProduct);
	}
}
